package com.estafet.microservices.scrum.basic.ui.selenium.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

public class SprintPage extends Page {
	
	@FindBys({
	    @FindBy(xpath = "//span[contains(text(),'Story #9869')]")
	})
	@CacheLookup
	List<WebElement> sprintStoryLinks;
	
	@FindBys({
	    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[4]/div[2]/div[8]/table[1]/tbody[1]/tr/td[1]/a")
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
	
	public SprintPage(String projectId, String sprintId) {
		super(sprintId);
	}

	public SprintPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public String uri() {
		return "/project/{1}/sprint/{2}";
	}
	
	public SprintBoardPage sprintBoardPage() {
		return click(sprintBoardLink, SprintBoardPage.class);
	}
	
	public SprintBurndownPage sprintBurndownPage() {
		return click(sprintBurndownLink, SprintBurndownPage.class);
	}
	
	public ProjectsPage projectsBreadCrumb() {
		return click(projectsBreadcrumbLink, ProjectsPage.class);
	}
	
	public ProjectPage projectBreadCrumb() {
		return click(projectBreadcrumbLink, ProjectPage.class);
	}
		
	public List<String> getSprintStories() {
		return getTextList(sprintStoryLinks);
	}

}
