package com.estafet.microservices.scrum.basic.ui.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

public class UpdateTaskHoursPage extends Page {

	@FindBy(css = "input")
	@CacheLookup
	WebElement submitButton;
	
	public UpdateTaskHoursPage(String projectId) {
		super(projectId);
	}

	public UpdateTaskHoursPage(WebDriver driver) {
		super(driver);
	}

	public ProjectPage projectPage() {
		return click(submitButton, ProjectPage.class);
	}

	@Override
	public String uri() {
		return "/project/{1}/sprint/{2}/task/{3}/update";
	}

}