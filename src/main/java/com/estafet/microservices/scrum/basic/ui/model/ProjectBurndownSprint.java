package com.estafet.microservices.scrum.basic.ui.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectBurndownSprint {

	private Integer id;

	private Integer number;

	private Integer pointsTotal;

	private float idealPointsTotal;

	private String status;

	public Integer getId() {
		return id;
	}

	public float getIdealPointsTotal() {
		return idealPointsTotal;
	}

	public Integer getNumber() {
		return number;
	}

	public Integer getPointsTotal() {
		return pointsTotal;
	}

	public String getStatus() {
		return status;
	}

	public String getName() {
		return "Sprint #" + number;
	}

}
