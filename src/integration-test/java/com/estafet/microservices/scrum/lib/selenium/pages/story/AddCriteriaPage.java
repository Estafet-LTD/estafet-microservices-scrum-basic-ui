package com.estafet.microservices.scrum.lib.selenium.pages.story;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import com.estafet.microservices.scrum.lib.selenium.pages.Page;

public class AddCriteriaPage extends Page {

	@FindBy(css = "input")
	@CacheLookup
	WebElement submitButton;
	
	public AddCriteriaPage(String storyId) {
		super(storyId);
	}

	public AddCriteriaPage(WebDriver driver) {
		super(driver);
	}

	public StoryPage clickSubmitButton() {
		return click(submitButton, StoryPage.class);
	}

	@Override
	public String title() {
		return "Simple Scrum Project Management";
	}

	@Override
	public String uri() {
		return "/criteria/{1}";
	}

}
