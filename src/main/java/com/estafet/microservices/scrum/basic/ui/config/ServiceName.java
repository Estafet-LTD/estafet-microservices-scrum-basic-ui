package com.estafet.microservices.scrum.basic.ui.config;

public enum ServiceName {
	PROJECT_SERVICE("project-api"),
	SPRINT_SERVICE("sprint-api"),
	STORY_SERVICE("story-api"),
	TASK_SERVICE("task-api"),
	HEALTH_SERVICE("health-api");
	
	private String  serviceName;
	
	private ServiceName(String serviceName) {
		this.setServiceName(serviceName);
	}

	public String getServiceName() {
		return serviceName;
	}

	private void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
}
