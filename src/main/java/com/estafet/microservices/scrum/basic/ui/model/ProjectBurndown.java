package com.estafet.microservices.scrum.basic.ui.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
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

	public List<ProjectBurndownSprint> getSprints() {
		return sprints;
	}
	
	public List<String> getSprintNames() {
		List<String> names = new ArrayList<String>(sprints.size());
		for (ProjectBurndownSprint sprint : sprints) {
			names.add(sprint.getName());
		}
		return names;
	}

	public List<Float> getIdeal() {
		List<Float> ideal = new ArrayList<Float>(sprints.size());
		for (ProjectBurndownSprint sprint : sprints) {
			ideal.add(sprint.getIdealPointsTotal());
		}
		return ideal;
	}
	
	public List<Integer> getActual() {
		List<Integer> actual = new ArrayList<Integer>(sprints.size());
		for (ProjectBurndownSprint sprint : sprints) {
			actual.add(sprint.getPointsTotal());
		}
		return actual;
	}
	
}
