node("maven") {

	def project = "dev"
	def microservice = "basic-ui"

	stage("checkout") {
		git branch: "master", url: "https://github.com/Estafet-LTD/estafet-microservices-scrum-basic-ui"
	}

	stage("build and execute unit tests") {
		sh "mvn clean test"
		post {
			always {
				junit "**/target/surefire-reports/*.xml"
			}
		}
	}

	stage("build & deploy container") {
		openshiftBuild namespace: ${project}, buildConfig: ${microservice}, showBuildLogs: "true",  waitTime: "3000000"
	}
  	  
	stage("verify container deployment") {
		openshiftVerifyDeployment namespace: ${project}, depCfg: ${microservice}, replicaCount:"1", verifyReplicaCount: "true", waitTime: "300000"	
	}

	stage("execute the container tests") {
		withEnv(
			[ "BASIC_UI_URI=http://${microservice}.${project}.svc:8080" ]) {
			sh "mvn verify -P integration-test"
		}
		post {
			always {
				junit "**/target/failsafe-reports/*.xml"
			}
		}
	}

}
