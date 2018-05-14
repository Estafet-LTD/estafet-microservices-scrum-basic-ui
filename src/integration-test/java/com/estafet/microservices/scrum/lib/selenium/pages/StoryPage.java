package com.estafet.microservices.scrum.lib.selenium.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

public class StoryPage extends Page {

	@FindBy(xpath = "/html[1]/body[1]/div[1]/div[4]/div[2]/div[4]/span[1]")
	@CacheLookup
	WebElement status;		
	
	@FindBy(xpath = "/html[1]/body[1]/div[1]/div[4]/div[2]/div[1]/h1[1]/small[1]")
	@CacheLookup
	WebElement name;
	
	@FindBy(xpath = "/html[1]/body[1]/div[1]/div[4]/div[2]/div[3]/span[1]")
	@CacheLookup
	WebElement points;
	
	@FindBy(linkText = "Add Crtieria")
	@CacheLookup
	WebElement addCriteriaLink;
	
	@FindBy(linkText = "Add Task")
	@CacheLookup
	WebElement addTaskLink;
	
	@FindBy(linkText = "Projects")
	@CacheLookup
	WebElement projectsBreadcrumbLink;
	
	@FindBy(linkText = "Project")
	@CacheLookup
	WebElement projectBreadcrumbLink;
	
	@FindBy(linkText = "Sprint")
	@CacheLookup
	WebElement sprintBreadcrumbLink;
	
	@FindBy(linkText = "Story")
	@CacheLookup
	WebElement storyBreadcrumbLink;
	
	@FindBys({
		@FindBy(xpath = "/html[1]/body[1]/div[1]/div[4]/div[2]/div[6]/table[1]/tbody[1]/tr/td[1]")
	})
	@CacheLookup
	List<WebElement> tasks;
	
	@FindBys({
	    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[4]/div[2]/div[5]/ol[1]/li/span[1]")
	})
	@CacheLookup
	List<WebElement> acceptanceCriteria;
	
	public StoryPage(String storyId) {
		super(storyId);
	}

	public StoryPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public String uri() {
		return "/story/{1}";
	}
	
	@Override
	public String title() {
		return "Simple Scrum Project Management";
	}
	
	public String getStatus() {
		return status.getText();
	}
	
	public String getName() {
		return name.getText();
	}
		
	public ProjectsPage clickProjectsBreadCrumbLink() {
		return click(projectsBreadcrumbLink, ProjectsPage.class);
	}
	
	public ProjectPage clickProjectBreadCrumbLink() {
		return click(projectBreadcrumbLink, ProjectPage.class);
	}
	
	public AddTaskPage clickAddTaskLink() {
		return click(addTaskLink, AddTaskPage.class);
	}
	
	public AddCriteriaPage clickAddCriteriaLink() {
		return click(addCriteriaLink, AddCriteriaPage.class);
	}
	
	public List<String> getTasks() {
		return getTextList(tasks);
	}
	
	public List<String> getAcceptanceCriteria() {
		return getTextList(acceptanceCriteria);
	}
	
}
