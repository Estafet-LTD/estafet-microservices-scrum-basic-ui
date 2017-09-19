package com.microservices.scrum.basic.ui.model;

public class ProjectBurndownSprint {

	private Integer id;

	private Integer number;

	private Integer pointsTotal;

	private String status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getPointsTotal() {
		return pointsTotal;
	}

	public void setPointsTotal(Integer pointsTotal) {
		this.pointsTotal = pointsTotal;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getName() {
		return "Sprint #" + number;
	}

}
