package com.estafet.microservices.scrum.basic.ui.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

public class AddTaskPage extends Page {

	@FindBy(css = "input")
	@CacheLookup
	WebElement submitButton;
	
	public AddTaskPage(String storyId) {
		super(storyId);
	}

	public AddTaskPage(WebDriver driver) {
		super(driver);
	}

	public ProjectPage projectPage() {
		return click(submitButton, ProjectPage.class);
	}

	@Override
	public String uri() {
		return "/task/{1}";
	}

}
