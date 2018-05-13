package com.estafet.microservices.scrum.basic.ui.selenium.tests;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.estafet.microservices.scrum.lib.selenium.pages.ProjectPage;

public class ITProjectPageTest {

	ProjectPage projectPage;
	
	@Before
	public void before() {
		projectPage = new ProjectPage("2");
	}

	@After
	public void after() {
		projectPage.close();
	}

	@Test
	public void testViewBacklog() {
		assertThat(projectPage.getStories().size(), is(4));
		assertThat(projectPage.getStories().get(0), is("Story #7353"));
		assertThat(projectPage.getStories().get(1), is("Story #6482"));
	}

	@Test
	public void testClickStory() {
		assertTrue(projectPage.clickStoryLink("Story #9869").isLoaded("3"));
	}

	@Test
	public void testClickNewStory() {
		assertTrue(projectPage.clickAddStoryLink().isLoaded("2"));
	}

	@Test
	public void testClickProjectsBreadCrumb() {
		assertTrue(projectPage.clickProjectsBreadCrumbLink().isLoaded());
	}
	
	@Test
	public void testClickProjectBreadCrumb() {
		assertTrue(projectPage.clickProjectBreadCrumbLink().isLoaded("2"));
	}
	
	@Test
	public void testClickProjectBurndown() {
		assertTrue(projectPage.clickProjectBurndownLink().isLoaded("2"));
	}

	@Test
	public void testClickSprintBurndown() {
		assertTrue(projectPage.clickSprintBurndownLink().isLoaded("2", "1"));
	}
	
	@Test
	public void testActiveSprint() {
		assertTrue(projectPage.clickActiveSprintLink().isLoaded("2", "1"));
	}

}
