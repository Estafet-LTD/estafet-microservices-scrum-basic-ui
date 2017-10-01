package com.estafet.microservices.scrum.basic.ui.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SprintBurndown {

	private Integer id;

	private Integer number;

	private List<SprintBurndownDay> sprintDays = new ArrayList<SprintBurndownDay>();

	public Integer getId() {
		return id;
	}

	public Integer getNumber() {
		return number;
	}

	public List<SprintBurndownDay> getSprintDays() {
		return sprintDays;
	}

}
