package com.estafet.microservices.scrum.lib.selenium.pages.sprint;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.estafet.microservices.scrum.lib.selenium.pages.task.UpdateTaskHoursPage;

public class SprintBoardPageInProgressTask {

	WebElement task;
	WebDriver driver;

	public SprintBoardPageInProgressTask(WebElement task, WebDriver driver) {
		this.task = task;
		this.driver = driver;
	}
		
	public SprintBoardPage complete() {
		task.findElement(By.xpath(".//div[3]/a[1]")).click();
		return new SprintBoardPage(driver);
	}
	
	public UpdateTaskHoursPage clickHoursLink() {
		task.findElement(By.xpath(".//div[1]/a[1]")).click();
		return new UpdateTaskHoursPage(driver);
	}
	
	public String getHours() {
		return task.findElement(By.xpath(".//a")).getText().replaceAll("\\s+", " ").replaceAll("\\[", "").replaceAll("\\]", "");
	}
	
	public String getTaskTitle() {
		return task.findElement(By.xpath(".//span")).getText();
	}
}
