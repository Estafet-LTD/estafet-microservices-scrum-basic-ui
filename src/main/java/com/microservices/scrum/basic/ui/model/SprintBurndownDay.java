package com.microservices.scrum.basic.ui.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SprintBurndownDay {

	private Integer dayNo;

	private Integer hoursTotal;

	private String sprintDay;

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
