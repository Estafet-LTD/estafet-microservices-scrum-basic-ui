package com.microservices.scrum.basic.ui.model;

import java.util.ArrayList;
import java.util.List;

public class SprintBoard {

	private Sprint sprint;

	private List<Task> todo = new ArrayList<Task>();

	private List<Task> inProgress = new ArrayList<Task>();

	private List<Task> completed = new ArrayList<Task>();

	public Sprint getSprint() {
		return sprint;
	}

	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}

	public List<Task> getTodo() {
		return todo;
	}

	public void setTodo(List<Task> todo) {
		this.todo = todo;
	}

	public List<Task> getInProgress() {
		return inProgress;
	}

	public void setInProgress(List<Task> inProgress) {
		this.inProgress = inProgress;
	}

	public List<Task> getCompleted() {
		return completed;
	}

	public void setCompleted(List<Task> completed) {
		this.completed = completed;
	}

	

}
