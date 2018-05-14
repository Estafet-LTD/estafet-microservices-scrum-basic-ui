package com.estafet.microservices.scrum.lib.selenium.pages;

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
		return null;
	}
	
	public String getName() {
		return task.findElement(By.xpath(".//span")).getText();
	}
	
}
