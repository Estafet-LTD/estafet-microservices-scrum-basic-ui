package com.estafet.microservices.scrum.basic.ui.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SprintBoardPage extends Page {
	
	@FindBy(linkText = "Projects")
	WebElement projectsBreadcrumbLink;
	
	@FindBy(linkText = "Project")
	WebElement projectBreadcrumbLink;
	
	@FindBy(linkText = "Sprint")
	WebElement sprintBreadcrumbLink;
		
	public SprintBoardPage(String projectId, String sprintId) {
		super(projectId, sprintId);
	}

	public SprintBoardPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public String uri() {
		return "/project/{1}/sprint/{2}/board";
	}
	
	public ProjectsPage projectsPage() {
		return click(projectsBreadcrumbLink, ProjectsPage.class);
	}
	
	public ProjectPage projectPage() {
		return click(projectBreadcrumbLink, ProjectPage.class);
	}
	
	public SprintPage sprintPage() {
		return click(sprintBreadcrumbLink, SprintPage.class);
	}
	
}
