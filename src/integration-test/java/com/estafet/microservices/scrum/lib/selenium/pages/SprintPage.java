package com.estafet.microservices.scrum.lib.selenium.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

public class SprintPage extends Page {
	
	@FindBys({
		@FindBy(xpath = "//div[7]/table[1]/tbody[1]/tr/td[1]/a")
	})
	@CacheLookup
	List<WebElement> activeStoriesLinks;
	
	@FindBys({
	    @FindBy(xpath = "//div[8]/table[1]/tbody[1]/tr/td[1]/a")
	})
	@CacheLookup
	List<WebElement> availableStoryLinks;
	
	@FindBy(linkText = "Sprint Board")
	@CacheLookup
	WebElement sprintBoardLink;
	
	@FindBy(linkText = "Sprint Burndown")
	@CacheLookup
	WebElement sprintBurndownLink;
	
	@FindBy(linkText = "Projects")
	@CacheLookup
	WebElement projectsBreadcrumbLink;
	
	@FindBy(linkText = "Project")
	@CacheLookup
	WebElement projectBreadcrumbLink;
	
	@FindBy(xpath = "/html[1]/body[1]/div[1]/div[4]/div[2]/div[2]/span[1]")
	@CacheLookup
	WebElement status;		
	
	@FindBy(xpath = "/html[1]/body[1]/div[1]/div[4]/div[2]/div[1]/h2[1]/small[1]")
	@CacheLookup
	WebElement name;
	
	public SprintPage(String projectId, String sprintId) {
		super(projectId, sprintId);
	}

	public SprintPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public String uri() {
		return "/project/{1}/sprint/{2}";
	}
	
	@Override
	public String title() {
		return "Simple Scrum Project Management";
	}
	
	public SprintBoardPage clickSprintBoardLink() {
		return click(sprintBoardLink, SprintBoardPage.class);
	}
	
	public SprintBurndownPage clikcSprintBurndownLink() {
		return click(sprintBurndownLink, SprintBurndownPage.class);
	}
	
	public ProjectsPage clickProjectsBreadCrumbLink() {
		return click(projectsBreadcrumbLink, ProjectsPage.class);
	}
	
	public ProjectPage clickProjectBreadCrumbLink() {
		return click(projectBreadcrumbLink, ProjectPage.class);
	}
		
	public List<String> getSprintStories() {
		return getTextList(activeStoriesLinks);
	}
	
	public List<String> getAvailableStories() {
		return getTextList(availableStoryLinks);
	}

	public StoryPage clickAvailableStoryLink(String story) {
		return click(story, availableStoryLinks, StoryPage.class);
	}
	
	public StoryPage clickSprintStoryLink(String story) {
		return click(story, activeStoriesLinks, StoryPage.class);
	}
	
	public String getStatus() {
		return status.getText();
	}
	
	public String getName() {
		return name.getText();
	}
	
}
