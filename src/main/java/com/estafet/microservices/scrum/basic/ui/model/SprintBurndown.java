package com.estafet.microservices.scrum.basic.ui.model;

import java.util.ArrayList;
import java.util.Arrays;
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
	
	public String getName() {
		return "Sprint #" + number;
	}

	public List<SprintBurndownDay> getSprintDays() {
		return sprintDays;
	}
	
	public List<String> getDays() {
		List<String> days = new ArrayList<String>(sprintDays.size());
		days.add("");
		String[] array = new String[] {"Day 1", "Day 2", "Day 3", "Day 4", "Day 5", "Day 6", "Day 7", "Day 8", "Day 9", "Day 10"};
		
		/*for (SprintBurndownDay day : sprintDays) {
			days.add(day.getSprintDay().substring(10));
		}*/
		return Arrays.asList(array);
	}

}
