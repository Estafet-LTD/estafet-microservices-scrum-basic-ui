@NonCPS
def getImageStreams(is) {
	def count = 0
	def imageStreams = []
	is.split('\n').each { line ->
    if (count > 0) {
        def matcher = line =~ /(\w+\-\w+)(.*)/
        imageStreams << matcher[0][1]
    }
    count++
	}
	return imageStreams
}

@NonCPS
def getImage(json) {
	def imageId = new groovy.json.JsonSlurper().parseText(json).status.containerStatuses[0].imageID
	def matcher = imageId =~ /(.*\@sha256\:)(\w+)/
	return matcher[0][2]
}

@NonCPS
def getLatest(json) {
	def tags = new groovy.json.JsonSlurper().parseText(json).status.tags
	for (int i = 0; i < tags.size(); i++) {
		if (tags[i]['tag'].equals("latest")) {
			def image = tags[i]['items'][0]['image']
			def matcher = image =~ /(sha256\:)(\w+)/
			return matcher[0][2]
		}
	}
	return null
}

def isLatestImageDeployed(microservice) {
	def pod = getPod microservice
	if (pod == null) return false
	def podImage = getPodImage pod
	def latestImage = getLatestImage microservice
	return podImage.equals(latestImage)
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


def microservice = "basic-ui"


node('maven') {

	properties([
	  parameters([
	     string(name: 'GITHUB'),
	  ])
	])

	stage("checkout") {
		git branch: "master", url: "https://github.com/${params.GITHUB}/estafet-microservices-scrum-basic-ui"
	}
	
	// derive the environment to deploy to
	def project = "test"
	
	// perform health check to check if deploed
	
	// get the image and check if referenced
	
	// deploy
	
	stage("create deployment config") {
		sh "oc process -n ${project} -f openshift/templates/${microservice}-config.yml -p NAMESPACE=${project} -p DOCKER_IMAGE_LABEL=PrepareForTesting | oc apply -f -"
		sh "oc set env dc/${microservice} SPRINT_API_SERVICE_URI=http://sprint-api.${project}.svc:8080 STORY_API_SERVICE_URI=http://story-api.${project}.svc:8080 TASK_API_SERVICE_URI=http://task-api.${project}.svc:8080 PROJECT_API_SERVICE_URI=http://project-api.${project}.svc:8080 SPRINT_BOARD_API_SERVICE_URI=http://sprint-board.${project}.svc:8080 SPRINT_BURNDOWN_SERVICE_URI=http://sprint-burndown.${project}.svc:8080 PROJECT_BURNDOWN_SERVICE_URI=http://project-burndown.${project}.svc:8080 JAEGER_AGENT_HOST=jaeger-agent.${project}.svc JAEGER_SAMPLER_MANAGER_HOST_PORT=jaeger-agent.${project}.svc:5778 JAEGER_SAMPLER_PARAM=1 JAEGER_SAMPLER_TYPE=const -n ${project}"
	}
	
	stage("execute deployment") {
		openshiftDeploy namespace: project, depCfg: microservice,  waitTime: "3000000"
		openshiftVerifyDeployment namespace: project, depCfg: microservice, replicaCount:"1", verifyReplicaCount: "true", waitTime: "300000" 
	}


}
