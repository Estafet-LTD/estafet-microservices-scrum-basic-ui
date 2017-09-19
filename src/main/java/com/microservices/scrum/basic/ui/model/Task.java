package com.microservices.scrum.basic.ui.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Task {

	private Integer id;

	private String title;

	private String description;

	private Integer initialHours;

	@JsonInclude(Include.NON_NULL)
	private Integer remainingHours;

	private String status = "Not Started";

	public Task setRemainingHours(Integer remainingHours) {
		this.remainingHours = remainingHours;
		return this;
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

	public Integer getInitialHours() {
		return initialHours;
	}

	public Integer getRemainingHours() {
		return remainingHours;
	}

	public String getStatus() {
		return status;
	}

	public Task setTitle(String title) {
		this.title = title;
		return this;
	}

	public Task setDescription(String description) {
		this.description = description;
		return this;
	}

	public Task setInitialHours(Integer initialHours) {
		this.initialHours = initialHours;
		return this;
	}

}
