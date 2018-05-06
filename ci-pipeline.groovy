node("maven") {

	def project = "dev"
	def microservice = "basic-ui"

	stage("checkout") {
		git branch: "master", url: "https://github.com/Estafet-LTD/estafet-microservices-scrum-basic-ui"
	}

	stage("unit tests") {
		try {
			sh "mvn clean test"
		} finally {
			junit "**/target/surefire-reports/*.xml"
		}
	}

	stage("update wiremock") {
		sh "oc get pods --selector app=wiremock-docker -o json -n ${project} > pods.json"
		def json = readFile('pods.json');
		def pod = new groovy.json.JsonSlurper().parseText(json).items[0].metadata.name
		sh "oc rsync src/integration-test/resources ${pod}:/home/wiremock/mappings -n ${project}"
		openshiftBuild namespace: project, buildConfig: "wiremock-docker", showBuildLogs: "true",  waitTime: "3000000"
		openshiftVerifyDeployment namespace: project, depCfg: "wiremock-docker", replicaCount:"1", verifyReplicaCount: "true", waitTime: "300000"
	}

	stage("build & deploy container") {
		openshiftBuild namespace: project, buildConfig: microservice, showBuildLogs: "true",  waitTime: "3000000"
	}
  	  
	stage("verify container deployment") {
		openshiftVerifyDeployment namespace: project, depCfg: microservice, replicaCount:"1", verifyReplicaCount: "true", waitTime: "300000"	
	}

	stage("container tests") {
		try {
			withEnv(
				[ "BASIC_UI_URI=http://${microservice}.${project}.svc:8080" ]) {
				sh "mvn verify -P integration-test"
			}
		} finally {
			junit "**/target/failsafe-reports/*.xml"
		}
	}
	
	stage("tag container for testing") {
		openshiftTag namespace: project, srcStream: microservice, srcTag: 'latest', destinationNamespace: 'test', destinationStream: microservice, destinationTag: 'PrepareForTesting'
	}

}

