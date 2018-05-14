package com.estafet.microservices.scrum.lib.selenium.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

public class HomePage extends Page {

	@FindBy(linkText = "here.")
	@CacheLookup
	WebElement projectsLink;
		
	public ProjectsPage clickHereLink() {
		return click(projectsLink, ProjectsPage.class);
	}

	@Override
	public String title() {
		return "Welcome!";
	}

	@Override
	public String uri() {
		return "/";
	}

}
