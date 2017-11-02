package com.estafet.microservices.scrum.basic.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Story {

	@JsonIgnore
	private RestTemplate restTemplate;
	
	private Integer id;

	private Integer projectId;

	private String title;

	private String description;

	private Integer storypoints;

	private Integer sprintId;

	private String status;

	private Project project;

	private List<AcceptanceCriterion> criteria = new ArrayList<AcceptanceCriterion>();

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Integer getStorypoints() {
		return storypoints;
	}

	public Integer getSprintId() {
		return sprintId;
	}

	public String getStatus() {
		return status;
	}

	public List<AcceptanceCriterion> getCriteria() {
		return criteria;
	}

	@SuppressWarnings("unchecked")
	public List<Task> getTasks() {
		if (id != null) {
			return restTemplate.getForObject(System.getenv("TASK_API_SERVICE_URI") + "/story/{storyId}/tasks",
					List.class, id);	
		} else {
			return new ArrayList<Task>();
		}
		
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStorypoints(Integer storypoints) {
		this.storypoints = storypoints;
	}

	@JsonIgnore
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

}
