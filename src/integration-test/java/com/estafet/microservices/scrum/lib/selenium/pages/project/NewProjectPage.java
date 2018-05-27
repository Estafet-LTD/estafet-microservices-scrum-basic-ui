package com.estafet.microservices.scrum.lib.selenium.pages.project;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import com.estafet.microservices.scrum.lib.selenium.pages.Page;

public class NewProjectPage extends Page {

	@FindBy(xpath = "//input[@type='submit']")
	@CacheLookup
	WebElement submitButton;
	
	@FindBy(xpath = "//input[@id='title']")
	@CacheLookup
	WebElement titleField;
	
	@FindBy(xpath = "//input[@id='noSprints']")
	@CacheLookup
	WebElement noSprintsField;
	
	@FindBy(xpath = "//input[@id='sprintLengthDays']")
	@CacheLookup
	WebElement sprintLengthDaysField;
	
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
	
	public NewProjectPage setProjectTitle(String title) {
		setField(titleField, title);
		return this;
	}
	
	public NewProjectPage setNoSprints(int noSprints) {
		setField(noSprintsField, noSprints);
		return this;
	}
	
	public NewProjectPage setSprintLengthDays(int sprintLength) {
		setField(sprintLengthDaysField, sprintLength);
		return this;
	}

}
