package com.estafet.microservices.scrum.lib.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

public class SprintBurndownPage extends Page {
	
	@CacheLookup
	@FindBy(linkText = "Projects")
	WebElement projectsBreadcrumbLink;
	
	@CacheLookup
	@FindBy(linkText = "Project")
	WebElement projectBreadcrumbLink;
	
	@CacheLookup
	@FindBy(linkText = "Sprint")
	WebElement sprintBreadcrumbLink;
		
	public SprintBurndownPage(String projectId, String sprintId) {
		super(projectId, sprintId);
	}

	public SprintBurndownPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public String uri() {
		return "/project/{1}/sprint/{2}/burndown";
	}
	
	@Override
	public String title() {
		return "Simple Scrum Project Management";
	}
	
	public ProjectsPage clickProjectsBreadCrumbLink() {
		return click(projectsBreadcrumbLink, ProjectsPage.class);
	}
	
	public ProjectPage clickProjectBreadCrumbLink() {
		return click(projectBreadcrumbLink, ProjectPage.class);
	}
	
	public SprintPage clickSprintBreadCrumbLink() {
		return click(sprintBreadcrumbLink, SprintPage.class);
	}
	
}
