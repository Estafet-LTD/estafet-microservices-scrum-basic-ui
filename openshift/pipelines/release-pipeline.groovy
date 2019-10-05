@NonCPS
def getVersions(json) {
	def tags = new groovy.json.JsonSlurper().parseText(json).status.tags
	def versions = []
	for (int i = 0; i < tags.size(); i++) {
		versions << tags[i]['tag'].replaceAll("\\-SNAPSHOT", "")
	}
	return versions
}

def username() {
    withCredentials([usernamePassword(credentialsId: 'microservices-scrum', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
        return USERNAME
    }
}

def password() {
    withCredentials([usernamePassword(credentialsId: 'microservices-scrum', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
        return PASSWORD
    }
}

def recentVersion( versions ) {
	def sorted = versions.sort(false) { a, b -> 

  	List verA = a.tokenize('.')
  	List verB = b.tokenize('.')

  	def commonIndices = Math.min(verA.size(), verB.size())

	  for (int i = 0; i < commonIndices; ++i) {
	  	def numA = verA[i].toInteger()
	    def numB = verB[i].toInteger()
	     println "comparing $numA and $numB"
	     if (numA != numB) {
	     	return numA <=> numB
	     }
	  }

  	// If we got this far then all the common indices are identical, so whichever version is longer must be more recent
  	verA.size() <=> verB.size()
	}

  println "sorted versions: $sorted"
  sorted[-1]
}

def getLatestVersion(microservice) {
	println "get latest image version for ${microservice}"
	println "oc get is ${microservice} -o json -n test > image.json"
	sh "oc get is ${microservice} -o json -n test > image.json"
	print "now read the file"
	def image = readFile('image.json')
	println "pre getVersions"
	def versions = getVersions(image)
	println "post getVersions"
	if (versions.size() == 0) {
		throw new RuntimeException("There are no images for ${microservice}")
	}
	return "${recentVersion(versions)}-SNAPSHOT"
}

def project = "test"
def microservice = "basic-ui"

def developmentVersion
def releaseVersion

node('maven') {

	properties([
	  parameters([
	     string(name: 'GITHUB'),
	  ])
	])

	stage("checkout") {
		git branch: "master", url: "https://${username()}:${password()}@github.com/${params.GITHUB}/estafet-microservices-scrum-basic-ui"
	}

	stage ("verify build image") {
		def version = getLatestVersion microservice
		def pom = readFile('pom.xml')
		def matcher = new XmlSlurper().parseText(pom).version =~ /(\d+\.\d+\.)(\d+)(\-SNAPSHOT)/
		def pomVersion = "${matcher[0][1]}${matcher[0][2].toInteger()}-SNAPSHOT"
		if (!version.equals(pomVersion)) {
			throw new RuntimeException("Source version ${pomVersion} does not match image version ${version}")
		}
	}

	stage("increment version") {
		def pom = readFile('pom.xml');
		def matcher = new XmlSlurper().parseText(pom).version =~ /(\d+\.\d+\.)(\d+)(\-SNAPSHOT)/
		developmentVersion = "${matcher[0][1]}${matcher[0][2].toInteger()+1}-SNAPSHOT"
		releaseVersion = "${matcher[0][1]}${matcher[0][2]}"
	}
	
	stage("perform release") {
        sh "git config --global user.email \"jenkins@estafet.com\""
        sh "git config --global user.name \"jenkins\""
        withMaven(mavenSettingsConfig: 'microservices-scrum') {
			sh "mvn release:clean release:prepare release:perform -DreleaseVersion=${releaseVersion} -DdevelopmentVersion=${developmentVersion} -DpushChanges=false -DlocalCheckout=true -DpreparationGoals=initialize -B"
			sh "git push origin master"
			sh "git tag ${releaseVersion}"
			sh "git push origin ${releaseVersion}"
		} 
	}	

	stage("create build config") {
			sh "oc process -n ${project} -f openshift/templates/${microservice}-build-config.yml -p NAMESPACE=${project} -p GITHUB=${params.GITHUB} -p SOURCE_REPOSITORY_REF=${releaseVersion} -p IMAGE_TAG=${releaseVersion} | oc apply -f -"
	}

	stage("execute build") {
		openshiftBuild namespace: project, buildConfig: microservice, waitTime: "300000"
		openshiftVerifyBuild namespace: project, buildConfig: microservice, waitTime: "300000" 
	}

	stage("create deployment config") {
		sh "oc process -n ${project} -f openshift/templates/${microservice}-config.yml -p NAMESPACE=${project} -p DOCKER_IMAGE_LABEL=${releaseVersion} | oc apply -f -"
		sh "oc set env dc/${microservice} SPRINT_API_SERVICE_URI=http://sprint-api.${project}.svc:8080 STORY_API_SERVICE_URI=http://story-api.${project}.svc:8080 TASK_API_SERVICE_URI=http://task-api.${project}.svc:8080 PROJECT_API_SERVICE_URI=http://project-api.${project}.svc:8080 SPRINT_BOARD_API_SERVICE_URI=http://sprint-board.${project}.svc:8080 SPRINT_BURNDOWN_SERVICE_URI=http://sprint-burndown.${project}.svc:8080 PROJECT_BURNDOWN_SERVICE_URI=http://project-burndown.${project}.svc:8080 JAEGER_AGENT_HOST=jaeger-agent.${project}.svc JAEGER_SAMPLER_MANAGER_HOST_PORT=jaeger-agent.${project}.svc:5778 JAEGER_SAMPLER_PARAM=1 JAEGER_SAMPLER_TYPE=const -n ${project}"
	}
	
	stage("execute deployment") {
		openshiftDeploy namespace: project, depCfg: microservice,  waitTime: "3000000"
		openshiftVerifyDeployment namespace: project, depCfg: microservice, replicaCount:"1", verifyReplicaCount: "true", waitTime: "300000" 
	}
	
	stage("increment version") {
		def pom = readFile('pom.xml');
		def matcher = new XmlSlurper().parseText(pom).version =~ /(\d+\.\d+\.)(\d+)(\-SNAPSHOT)/
		developmentVersion = "${matcher[0][1]}${matcher[0][2].toInteger()+1}-SNAPSHOT"
		releaseVersion = "${matcher[0][1]}${matcher[0][2]}"
	}
	
	stage("perform release") {
        sh "git config --global user.email \"jenkins@estafet.com\""
        sh "git config --global user.name \"jenkins\""
        withMaven(mavenSettingsConfig: 'microservices-scrum') {
			sh "mvn release:clean release:prepare release:perform -DreleaseVersion=${releaseVersion} -DdevelopmentVersion=${developmentVersion} -DpushChanges=false -DlocalCheckout=true -DpreparationGoals=initialize -B"
			sh "git push origin master"
			sh "git tag ${releaseVersion}"
			sh "git push origin ${releaseVersion}"
		} 
	}	

	stage("promote image to staging") {
		openshiftTag namespace: project, srcStream: microservice, srcTag: 'PrepareForTesting', destinationNamespace: 'staging', destinationStream: microservice, destinationTag: releaseVersion
	}	

}

