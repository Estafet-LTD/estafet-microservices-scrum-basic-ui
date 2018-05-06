package com.estafet.microservices.scrum.basic.ui.selenium.tests;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.estafet.microservices.scrum.basic.ui.selenium.pages.ProjectPage;

public class ITProjectPageTest {

	ProjectPage projectPage;
	
	@Before
	public void before() {
		projectPage = new ProjectPage("1");
	}

	@After
	public void after() {
		projectPage.close();
	}

	@Test
	public void testViewBacklog() {
		assertThat(projectPage.getStories().size(), is(4));
		assertThat(projectPage.getStories().get(0), is("Story #9869"));
		assertThat(projectPage.getStories().get(3), is("Story #6482"));
	}

	@Test
	public void testClickStory() {
		assertTrue(projectPage.storyPage("Story #9869").isLoaded("1"));
	}

	@Test
	public void testClickNewStory() {
		assertTrue(projectPage.addStoryPage().isLoaded("1"));
	}

	@Test
	public void testClickProjectsBreadCrumb() {
		assertTrue(projectPage.projectsBreadCrumb().isLoaded());
	}
	
	@Test
	public void testClickProjectBreadCrumb() {
		assertTrue(projectPage.projectBreadCrumb().isLoaded("1"));
	}
	
	@Test
	public void testClickProjectBurndown() {
		assertTrue(projectPage.projectBurndownPage().isLoaded("1"));
	}

	@Test
	public void testClickSprintBurndown() {
		assertTrue(projectPage.sprintBurndownPage().isLoaded("1", "1"));
	}
	
	@Test
	public void testActiveSprint() {
		assertTrue(projectPage.activeSprint().isLoaded("1", "1"));
	}

}
