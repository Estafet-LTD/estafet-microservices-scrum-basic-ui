@NonCPS
def getVersion(pom) {
	def matcher = new XmlSlurper().parseText(pom).version =~ /(\d+\.\d+\.)(\d+)(\-SNAPSHOT)/
	return "${matcher[0][1]}${matcher[0][2].toInteger()}-SNAPSHOT"
}

node("maven") {

	properties([
	  parameters([
	     string(name: 'GITHUB'), string(name: 'PRODUCT'),
	  ])
	])

	def project = "${params.PRODUCT}-build"
	def microservice = "basic-ui"
	def version

	currentBuild.description = "Build a container from the source, then execute unit and container integration tests before promoting the container as a release candidate for acceptance testing."

	stage("checkout") {
		git branch: "master", url: "https://github.com/${params.GITHUB}/estafet-microservices-scrum-basic-ui"
	}

	stage("update wiremock") {
		def files = findFiles(glob: 'src/integration-test/resources/*.json')
		files.each { file -> 
			def json = readFile(file.path)
			def response = httpRequest url: "http://wiremock-docker.${project}.svc:8080/__admin/mappings/new", httpMode: "POST", validResponseCodes: "201", requestBody: json
		}
	}
	
	stage("reset the promoted image stream") {
		def pom = readFile('pom.xml')
		version = getVersion(pom)
		sh "oc tag -d ${microservice}:${version} -n ${params.PRODUCT}-cicd || true"
	}

	stage("create build config") {
			sh "oc process -n ${project} -f openshift/templates/${microservice}-build-config.yml -p NAMESPACE=${project} -p GITHUB=${params.GITHUB} -p DOCKER_IMAGE_LABEL=${version} -p PRODUCT=${params.PRODUCT} | oc apply -f -"
	}

	stage("execute build") {
		openshiftBuild namespace: project, buildConfig: microservice
		openshiftVerifyBuild namespace: project, buildConfig: microservice, waitTime: "300000" 
	}

	stage("create deployment config") {
		sh "oc process -n ${project} -f openshift/templates/${microservice}-config.yml -p NAMESPACE=${project} -p DOCKER_NAMESPACE=${project} -p DOCKER_IMAGE_LABEL=${version} -p PRODUCT=${params.PRODUCT} | oc apply -f -"
		sh "oc set env dc/${microservice} SPRINT_API_SERVICE_URI=http://wiremock-docker.${project}.svc:8080/basic-ui/sprint-api STORY_API_SERVICE_URI=http://wiremock-docker.${project}.svc:8080/basic-ui/story-api TASK_API_SERVICE_URI=http://wiremock-docker.${project}.svc:8080/basic-ui/task-api PROJECT_API_SERVICE_URI=http://wiremock-docker.${project}.svc:8080/basic-ui/project-api SPRINT_BOARD_API_SERVICE_URI=http://wiremock-docker.${project}.svc:8080/basic-ui/sprint-board SPRINT_BURNDOWN_SERVICE_URI=http://wiremock-docker.${project}.svc:8080/basic-ui/sprint-burndown PROJECT_BURNDOWN_SERVICE_URI=http://wiremock-docker.${project}.svc:8080/basic-ui/project-burndown -n ${project}"
	}

	stage("execute deployment") {
		openshiftDeploy namespace: project, depCfg: microservice,  waitTime: "3000000"
		openshiftVerifyDeployment namespace: project, depCfg: microservice, replicaCount:"1", verifyReplicaCount: "true", waitTime: "300000" 
	}

	stage("container tests") {
			withMaven(mavenSettingsConfig: 'microservices-scrum') {
 				sh "mvn clean verify -P integration-test"
			} 
	}

	stage("deploy snapshots") {
		withMaven(mavenSettingsConfig: 'microservices-scrum') {
 			sh "mvn clean deploy -Dmaven.test.skip=true"
		} 
	}	
	
	stage("promote the image") {
		openshiftTag namespace: project, srcStream: microservice, srcTag: version, destinationNamespace: "${params.PRODUCT}-cicd", destinationStream: microservice, destinationTag: version
	}

}

