package com.estafet.microservices.scrum.lib.selenium.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

public class ProjectPage extends Page {
	
	@FindBys({
	    @FindBy(partialLinkText = "Story #")
	})
	@CacheLookup
	List<WebElement> storyLinks;
	
	@FindBy(linkText = "Project Burndown")
	@CacheLookup
	WebElement projectBurndownLink;
	
	@FindBy(linkText = "Sprint Burndown")
	@CacheLookup
	WebElement sprintBurndownLink;
	
	@FindBy(partialLinkText = "Sprint #")
	@CacheLookup
	WebElement activeSprintLink;
	
	@FindBy(linkText = "Add Story")
	@CacheLookup
	WebElement addStoryLink;
	
	@FindBy(linkText = "Projects")
	@CacheLookup
	WebElement projectsBreadcrumbLink;
	
	@FindBy(linkText = "Project")
	@CacheLookup
	WebElement projectBreadcrumbLink;
	
	public ProjectPage(String projectId) {
		super(projectId);
	}

	public ProjectPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public String uri() {
		return "/project/{1}";
	}
	
	@Override
	public String title() {
		return "Simple Scrum Project Management";
	}
	
	public List<String> getStories() {
		return getTextList(storyLinks);
	}
	
	public StoryPage clickStoryLink(String story) {
		return click(story, storyLinks, StoryPage.class);
	}
	
	public SprintPage clickActiveSprintLink() {
		return click(activeSprintLink, SprintPage.class);
	}

	public AddStoryPage clickAddStoryLink() {
		return click(addStoryLink, AddStoryPage.class);
	}
	
	public ProjectBurndownPage clickProjectBurndownLink() {
		return click(projectBurndownLink, ProjectBurndownPage.class);
	}
	
	public SprintBurndownPage clickSprintBurndownLink() {
		return click(sprintBurndownLink, SprintBurndownPage.class);
	}
	
	public ProjectsPage clickProjectsBreadCrumbLink() {
		return click(projectsBreadcrumbLink, ProjectsPage.class);
	}
	
	public ProjectPage clickProjectBreadCrumbLink() {
		return click(projectBreadcrumbLink, ProjectPage.class);
	}
	
}
