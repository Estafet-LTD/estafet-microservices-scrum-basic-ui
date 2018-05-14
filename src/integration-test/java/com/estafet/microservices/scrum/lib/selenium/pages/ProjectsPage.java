package com.estafet.microservices.scrum.lib.selenium.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

public class ProjectsPage extends Page {

	@FindBys({
	    @FindBy(css = "ul > li > a"),
	})
	@CacheLookup
	List<WebElement> projects;
	
	@FindBy(linkText = "new")
	@CacheLookup
	WebElement newProjectLink;
	
	public ProjectsPage() {
		super();
	}

	public ProjectsPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public String uri() {
		return "/projects";
	}
	
	@Override
	public String title() {
		return "Simple Scrum Project Management";
	}
	
	public List<String> getProjects() {
		return getTextList(projects);
	}
	
	public ProjectPage clickProjectLink(String project) {
		return click(project, projects, ProjectPage.class);
	}
	
	public NewProjectPage clickNewProjectLink() {
		return click(newProjectLink, NewProjectPage.class);
	}
	
}
