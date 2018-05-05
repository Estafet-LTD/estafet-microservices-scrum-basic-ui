pipeline {

	def project = "dev"
	def microservice = "basic-ui"
	
	agent {
		label 'maven'
	}
	
	environment { 
		BASIC_UI_URI="http://${microservice}.${project}.svc:8080" 
	}
	
  	stages {
		stage("checkout") {
			steps {
				git branch: "master", url: "https://github.com/Estafet-LTD/estafet-microservices-scrum-basic-ui"
			}
		}
    	stage ("unit tests") {
      		steps {
        		sh "mvn clean test"
      		}
    	}
		stage ("build & deploy container") {
			steps {
				openshiftBuild namespace: project, buildConfig: microservice, showBuildLogs: "true",  waitTime: "3000000"
			}
		}
		stage ("verify container deployment") {
			steps {
				openshiftVerifyDeployment namespace: project, depCfg: microservice, replicaCount:"1", verifyReplicaCount: "true", waitTime: "300000"
			}
		}
		stage ("execute the container tests") {
            steps {
            	sh "mvn verify -P integration-test"
            }
		}
		stage ("tag container as preparing for testing") {
			steps {
				openshiftTag namespace: project, srcStream: microservice, srcTag: 'latest', destinationNamespace: 'test', destinationStream: microservice, destinationTag: 'PrepareForTesting'
			}
		}    	
		post {
			always {
				junit '**/target/*-reports/*.xml'
			}
    	} 
	}
	     
}



