package com.estafet.microservices.scrum.lib.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

public class UpdateTaskHoursPage extends Page {

	@FindBy(css = "input")
	@CacheLookup
	WebElement submitButton;
	
	public UpdateTaskHoursPage(String projectId, String sprintId, String taskId) {
		super(projectId, sprintId, taskId);
	}

	public UpdateTaskHoursPage(WebDriver driver) {
		super(driver);
	}

	public ProjectPage clickSubmitButton() {
		return click(submitButton, ProjectPage.class);
	}

	@Override
	public String uri() {
		return "/project/{1}/sprint/{2}/task/{3}/update";
	}
	
	@Override
	public String title() {
		return "Simple Scrum Project Management";
	}

}
