package com.estafet.microservices.scrum.lib.selenium.pages.sprint;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SprintBoardPageCompletedTask {

	WebElement task;
	WebDriver driver;

	public SprintBoardPageCompletedTask(WebElement task, WebDriver driver) {
		this.task = task;
		this.driver = driver;
	}
	
	public String getTaskTitle() {
		return task.findElement(By.xpath(".//span")).getText();
	}
	
}
