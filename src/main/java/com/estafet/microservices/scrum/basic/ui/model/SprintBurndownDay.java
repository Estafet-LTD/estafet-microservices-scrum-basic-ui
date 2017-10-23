package com.estafet.microservices.scrum.basic.ui.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SprintBurndownDay {

	private Integer dayNo;

	private Integer hoursTotal;

	private Float idealHours;

	private String sprintDay;

	public Float getIdealHours() {
		return idealHours;
	}

	public Integer getDayNo() {
		return dayNo;
	}

	public Integer getHoursTotal() {
		return hoursTotal;
	}

	public String getSprintDay() {
		return sprintDay;
	}

}
