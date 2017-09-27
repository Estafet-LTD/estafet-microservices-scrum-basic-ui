package com.microservices.scrum.basic.ui.model;

import java.util.ArrayList;
import java.util.List;

public class Project {

	private int id;

	private String title;

	private List<Sprint> sprints = new ArrayList<Sprint>();

	private List<Story> stories = new ArrayList<Story>();

	public Project addSprints(List<Sprint> sprints) {
		this.sprints.addAll(sprints);
		return this;
	}
	
	public Project addStories(List<Story> stories) {
		this.stories.addAll(stories);
		return this;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Sprint> getSprints() {
		for (Sprint sprint : sprints) {
			sprint.setProjectId(id);
		}
		return sprints;
	}

	public Sprint getActiveSprint() {
		for (Sprint sprint : getSprints()) {
			if (sprint.getStatus().equals("Active")) {
				return sprint;
			}
		}
		return null;
	}

	public List<Sprint> getInactiveSprints() {
		List<Sprint> inactiveSprints = new ArrayList<Sprint>();
		for (Sprint sprint : getSprints()) {
			if (!sprint.getStatus().equals("Active")) {
				inactiveSprints.add(sprint);
			}
		}
		return inactiveSprints;
	}

	public List<Story> getStories() {
		return stories;
	}
	
	public String getName() {
		return "Project #" + id;
	}

}
