package com.estafet.microservices.scrum.lib.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SprintBoardPageInProgressTask {

	WebElement task;
	WebDriver driver;

	public SprintBoardPageInProgressTask(WebElement task, WebDriver driver) {
		this.task = task;
		this.driver = driver;
	}
		
	public SprintBoardPage complete() {
		return null;
	}
	
	public UpdateTaskHoursPage getHoursLink() {
		task.findElement(By.xpath(".//a")).click();
		return new UpdateTaskHoursPage(driver);
	}
	
	public String getHours() {
		return task.findElement(By.xpath(".//a")).getText().replaceAll("\\s+", " ").replaceAll("\\[", "").replaceAll("\\]", "");
	}
	
	public String getName() {
		return task.findElement(By.xpath(".//span")).getText();
	}
}
