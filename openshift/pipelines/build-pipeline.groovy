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
	def pipelines

	currentBuild.description = "Build a container from the source, then execute unit and container integration tests before promoting the container as a release candidate for acceptance testing."

	stage("checkout") {
		git branch: "master", url: "https://github.com/${params.GITHUB}/estafet-microservices-scrum-basic-ui"
	}
	
	stage("read the pipeline definition") {
		pipelines = readYaml file: "openshift/pipelines/pipelines.yml"
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
		println "pipelines.build.wiremock ${pipelines.build.wiremock}"
		if (pipelines.build.wiremock) {
			def envVars = ""
			println "pipelines.build.wiremock_environment_variables ${pipelines.build.wiremock_environment_variables}"
			println "type for pipelines.build.wiremock_environment_variables ${pipelines.build.wiremock_environment_variables.getClass()}"
			println "type for pipelines.build.wiremock_environment_variables[0] ${pipelines.build.wiremock_environment_variables[0].getClass()}"
			for (int i = 0; i < pipelines.build.wiremock_environment_variables.name.size(); i++) {
				def name = pipelines.build.wiremock_environment_variables.name[i]
				println "name ${pipelines.build.wiremock_environment_variables.name[i]}"
				def value = pipelines.build.wiremock_environment_variables.value[i]
				println "value ${value}"
				envVars = "${envVars} ${name}=${value}"
			}
			println "envVars ${envVars}"
			sh "oc set env dc/${microservice} ${envVars} -n ${project}"	
		}
	}

	stage("execute deployment") {
		openshiftDeploy namespace: project, depCfg: microservice
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

