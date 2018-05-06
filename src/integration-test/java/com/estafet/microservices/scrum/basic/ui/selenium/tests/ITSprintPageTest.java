package com.estafet.microservices.scrum.basic.ui.selenium.tests;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.estafet.microservices.scrum.basic.ui.selenium.pages.SprintPage;

@Ignore
public class ITSprintPageTest {

	SprintPage sprintPage;
	
	@Before
	public void before() {
		sprintPage = new SprintPage("1", "1");
	}

	@After
	public void after() {
		sprintPage.close();
	}

	@Test
	public void testViewSprintStories() {
		assertThat(sprintPage.getSprintStories().size(), is(2));
		assertThat(sprintPage.getSprintStories().get(0), is("Story #9869"));
		assertThat(sprintPage.getSprintStories().get(1), is("Story #4249"));
	}
	
	@Test
	public void testViewAvaiableStories() {
		assertThat(sprintPage.getAvailableStories().size(), is(2));
		assertThat(sprintPage.getAvailableStories().get(0), is("Story #7353"));
		assertThat(sprintPage.getAvailableStories().get(1), is("Story #6482"));
	}

	@Test
	public void testClickSprintStory() {
		assertTrue(sprintPage.sprintStory("Story #9869").isLoaded("1"));
	}
	
	@Test
	public void testClickAvailableStory() {
		assertTrue(sprintPage.availableStory("Story #6482").isLoaded("4"));
	}
	
	@Test
	public void testClickProjectsBreadCrumb() {
		assertTrue(sprintPage.projectsBreadCrumb().isLoaded());
	}
	
	@Test
	public void testClickProjectBreadCrumb() {
		assertTrue(sprintPage.projectBreadCrumb().isLoaded("1"));
	}
	
	@Test
	public void testClickSprintBoard() {
		assertTrue(sprintPage.sprintBoardPage().isLoaded("1", "1"));
	}

	@Test
	public void testClickSprintBurndown() {
		assertTrue(sprintPage.sprintBurndownPage().isLoaded("1", "1"));
	}
	
	@Test
	public void testStatus() {
		assertThat(sprintPage.getStatus(), is("Active"));
	}
	
	@Test
	public void testName() {
		assertThat(sprintPage.getName(), is("Sprint #1"));
	}
	
}
