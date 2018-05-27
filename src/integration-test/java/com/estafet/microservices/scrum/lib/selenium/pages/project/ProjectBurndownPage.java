package com.estafet.microservices.scrum.lib.selenium.pages.project;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import com.estafet.microservices.scrum.lib.selenium.pages.Page;

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
	
	@Override
	public String title() {
		return "Simple Scrum Project Management";
	}
	
	public ProjectListPage clickProjectsBreadCrumbLink() {
		return click(projectsBreadcrumbLink, ProjectListPage.class);
	}
	
	public ProjectPage clickProjectBreadCrumbLink() {
		return click(projectBreadcrumbLink, ProjectPage.class);
	}
	
	public Integer getProjectId() {
		return Integer.parseInt(getCurrentURI().replaceAll("\\/project\\/", "").replaceAll("\\/burndown\\/", ""));
	}
	
}
