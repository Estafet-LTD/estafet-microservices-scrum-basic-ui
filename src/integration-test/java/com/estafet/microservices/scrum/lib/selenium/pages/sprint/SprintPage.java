package com.estafet.microservices.scrum.lib.selenium.pages.sprint;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import com.estafet.microservices.scrum.lib.selenium.pages.Page;
import com.estafet.microservices.scrum.lib.selenium.pages.project.ProjectListPage;
import com.estafet.microservices.scrum.lib.selenium.pages.project.ProjectPage;
import com.estafet.microservices.scrum.lib.selenium.pages.story.StoryPage;

public class SprintPage extends Page {

	@FindBys({ @FindBy(xpath = "//div[7]/table[1]/tbody[1]/tr/td[1]/a") })
	@CacheLookup
	List<WebElement> activeStoriesLinks;

	@FindBys({ @FindBy(xpath = "//div[8]/table[1]/tbody[1]/tr/td[1]/a") })
	@CacheLookup
	List<WebElement> availableStoryLinks;

	@FindBys({ @FindBy(xpath = "/html[1]/body[1]/div[1]/div[4]/div[2]/div[8]/table[1]/tbody[1]/tr") })
	@CacheLookup
	List<WebElement> availableStories;

	@FindBys({ @FindBy(xpath = "/html[1]/body[1]/div[1]/div[4]/div[2]/div[7]/table[1]/tbody[1]/tr") })
	@CacheLookup
	List<WebElement> sprintStories;
	
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

	public ProjectListPage clickProjectsBreadCrumbLink() {
		return click(projectsBreadcrumbLink, ProjectListPage.class);
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

	public SprintPage clickAddToSprint(String story) {
		for (int i = 1; i < availableStories.size(); i++) {
			WebElement storyLink = availableStories.get(i).findElement(By.xpath(".//td[1]/a[1]"));
			if (storyLink.getText().equals(story)) {
				WebElement addToSprintLink = availableStories.get(i).findElement(By.xpath(".//td[4]/a[1]"));
				return click(addToSprintLink, SprintPage.class);
			}
		}
		throw new RuntimeException("cannot find avaiable story - " + story);
	}
	
	public boolean isAvailableStory(String story) {
		for (int i = 1; i < availableStories.size(); i++) {
			WebElement storyLink = availableStories.get(i).findElement(By.xpath(".//td[1]/a[1]"));
			if (storyLink.getText().equals(story)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isSprintStory(String story) {
		for (int i = 1; i < sprintStories.size(); i++) {
			WebElement storyLink = sprintStories.get(i).findElement(By.xpath(".//td[1]/a[1]"));
			if (storyLink.getText().equals(story)) {
				return true;
			}
		}
		return false;
	}
	
	public String getSprintStoryStatus(String story) {
		for (int i = 1; i < sprintStories.size(); i++) {
			WebElement storyCell = sprintStories.get(i).findElement(By.xpath(".//td[1]"));
			if (storyCell.getText().equals(story)) {
				WebElement status = sprintStories.get(i).findElement(By.xpath(".//td[3]"));
				return status.getText();
			}
		}
		throw new RuntimeException("cannot find sprint story - " + story);
	}
	
	public Integer getSprintId() {
		return Integer.parseInt(getCurrentURI().replaceAll("\\/project\\/\\d+", "").replaceAll("\\/sprint\\/", ""));
	}

}
