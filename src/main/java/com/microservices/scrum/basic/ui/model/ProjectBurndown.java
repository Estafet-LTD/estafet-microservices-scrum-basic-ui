package com.microservices.scrum.basic.ui.model;

import java.util.ArrayList;
import java.util.List;

public class ProjectBurndown {

	private int id;

	private String title;

	private List<ProjectBurndownSprint> sprints = new ArrayList<ProjectBurndownSprint>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<ProjectBurndownSprint> getSprints() {
		return sprints;
	}

	public void setSprints(List<ProjectBurndownSprint> sprints) {
		this.sprints = sprints;
	}

}
