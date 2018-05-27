package com.estafet.microservices.scrum.lib.selenium.pages.sprint;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SprintBoardPageToDoTask {

	WebElement task;
	WebDriver driver;

	public SprintBoardPageToDoTask(WebElement task, WebDriver driver) {
		this.task = task;
		this.driver = driver;
	}
	
	public SprintBoardPage claim() {
		task.findElement(By.xpath(".//div[3]/a[1]")).click();
		return new SprintBoardPage(driver);
	}
	
	public String getTaskTitle() {
		return task.findElement(By.xpath(".//div[1]/span[1]")).getText();
	}
	
	public String getStoryTitle() {
		return task.findElement(By.xpath(".//div[2]/em[1]/span[1]")).getText();
	}
	
}
