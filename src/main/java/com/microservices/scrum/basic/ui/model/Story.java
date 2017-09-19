package com.microservices.scrum.basic.ui.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Story {

	private int id;

	private Integer projectId;

	private String title;

	private String description;

	private Integer storypoints;

	@JsonInclude(Include.NON_NULL)
	private Integer sprintId;

	private String status;

	private Project project;

	private List<AcceptanceCriterion> criteria = new ArrayList<AcceptanceCriterion>();

	private List<Task> tasks = new ArrayList<Task>();

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

	public int getId() {
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

	public List<Task> getTasks() {
		return tasks;
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

}
