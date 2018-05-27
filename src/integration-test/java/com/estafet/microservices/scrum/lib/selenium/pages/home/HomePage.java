package com.estafet.microservices.scrum.lib.selenium.pages.home;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import com.estafet.microservices.scrum.lib.selenium.pages.Page;
import com.estafet.microservices.scrum.lib.selenium.pages.project.ProjectListPage;

public class HomePage extends Page {

	@FindBy(linkText = "here.")
	@CacheLookup
	WebElement projectsLink;
		
	public ProjectListPage clickHereLink() {
		return click(projectsLink, ProjectListPage.class);
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
