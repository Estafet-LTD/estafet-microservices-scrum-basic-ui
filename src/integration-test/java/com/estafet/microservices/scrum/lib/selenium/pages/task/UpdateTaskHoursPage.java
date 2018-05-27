package com.estafet.microservices.scrum.lib.selenium.pages.task;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import com.estafet.microservices.scrum.lib.selenium.pages.Page;
import com.estafet.microservices.scrum.lib.selenium.pages.project.ProjectPage;
import com.estafet.microservices.scrum.lib.selenium.pages.sprint.SprintBoardPage;

public class UpdateTaskHoursPage extends Page {

	@FindBy(xpath = "//input[@type='submit']")
	@CacheLookup
	WebElement submitButton;
	
	@FindBy(xpath = "//input[@id='remainingHours']")
	@CacheLookup
	WebElement remainingHoursField;
	
	public UpdateTaskHoursPage(String projectId, String sprintId, String taskId) {
		super(projectId, sprintId, taskId);
	}

	public UpdateTaskHoursPage(WebDriver driver) {
		super(driver);
	}

	public SprintBoardPage clickSubmitButton() {
		return click(submitButton, SprintBoardPage.class);
	}

	@Override
	public String uri() {
		return "/project/{1}/sprint/{2}/task/{3}/update";
	}
	
	@Override
	public String title() {
		return "Simple Scrum Project Management";
	}

	public UpdateTaskHoursPage setRemainingHours(int remainingHours) {
		setField(remainingHoursField, remainingHours);
		return this;
	}

}
