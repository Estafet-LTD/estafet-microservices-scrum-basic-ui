package com.estafet.microservices.scrum.basic.ui.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

public class ProjectBurndownPage extends Page {
	
	@FindBy(linkText = "Projects")
	@CacheLookup
	WebElement projectsBreadcrumbLink;
	
	@FindBy(linkText = "Project")
	@CacheLookup
	WebElement projectBreadcrumbLink;
		
	public ProjectBurndownPage(String projectId) {
		super(projectId);
	}

	public ProjectBurndownPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public String uri() {
		return "/project/{1}/burndown/";
	}
	
	public ProjectsPage projectsPage() {
		return click(projectsBreadcrumbLink, ProjectsPage.class);
	}
	
	public ProjectPage projectPage() {
		return click(projectBreadcrumbLink, ProjectPage.class);
	}
	
}
