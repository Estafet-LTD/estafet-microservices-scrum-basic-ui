package com.estafet.microservices.scrum.basic.ui.config;

public class UrlConstants {
	
//	public static final String STORY_API_SERVICE_URI = System.getenv("STORY_API_SERVICE_URI");
	public static final String STORY_API_SERVICE_URI = "http://gateway.microservices-scrum.svc:8080/story-api";
//	public static final String STORY_API_SERVICE_URI = "http://localhost:8085/story-api";
//	public static final String STORY_API_SERVICE_URI = "http://localhost:8089";
	
//	public static final String PROJECT_API_SERVICE_URI = System.getenv("PROJECT_API_SERVICE_URI");
	public static final String PROJECT_API_SERVICE_URI = "http://gateway.microservices-scrum.svc:8080/project-api";
//	public static final String PROJECT_API_SERVICE_URI = "http://localhost:8085/project-api";
//	public static final String PROJECT_API_SERVICE_URI = "http://localhost:8086";

	
//	public static final String SPRINT_API_SERVICE_URI = System.getenv("SPRINT_API_SERVICE_URI");
	public static final String SPRINT_API_SERVICE_URI = "http://gateway.microservices-scrum.svc:8080/sprint-api";
//	public static final String SPRINT_API_SERVICE_URI = "http://localhost:8085/sprint-api";
	
	public static final String TASK_API_SERVICE_URI = System.getenv("TASK_API_SERVICE_URI");
//	public static final String PROJECT_API_SERVICE_URI = "http://gateway.microservices-scrum.svc:8080/task-api";
//	public static final String TASK_API_SERVICE_URI = "http://localhost:8085/task-api";
	
}
