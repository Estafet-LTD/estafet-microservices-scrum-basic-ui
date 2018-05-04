package com.estafet.microservices.scrum.basic.ui.selenium.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

public class StoryPage extends Page {
		
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
	

}
