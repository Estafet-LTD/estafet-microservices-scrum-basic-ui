package com.estafet.microservices.scrum.basic.ui.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

public class NewProjectPage extends Page {

	@FindBy(css = "input")
	@CacheLookup
	WebElement submitButton;
	
	public NewProjectPage() {
		super();
	}

	public NewProjectPage(WebDriver driver) {
		super(driver);
	}

	public ProjectPage clickSubmitButton() {
		return click(submitButton, ProjectPage.class);
	}
	
	@Override
	public String title() {
		return "Simple Scrum Project Management";
	}

	@Override
	public String uri() {
		return "/newproject";
	}

}
