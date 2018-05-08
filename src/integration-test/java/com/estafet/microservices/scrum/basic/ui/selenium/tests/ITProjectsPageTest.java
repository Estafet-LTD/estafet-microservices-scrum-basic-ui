package com.estafet.microservices.scrum.basic.ui.selenium.tests;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.estafet.microservices.scrum.basic.ui.selenium.pages.ProjectsPage;

public class ITProjectsPageTest {

	ProjectsPage projectsPage;

	@Before
	public void before() throws Exception {
		projectsPage = new ProjectsPage();
	}

	@After
	public void after() {
		projectsPage.close();
	}

	@Test
	public void testViewProjects() {
		assertThat(projectsPage.getProjects().size(), is(2));
		assertThat(projectsPage.getProjects().get(0), is("My Project #9384"));
		assertThat(projectsPage.getProjects().get(1), is("My Project #1749"));
	}

	@Test
	public void testClickNewProject() {
		assertTrue(projectsPage.clickNewProjectLink().isLoaded());
	}

	@Test
	public void testClickProject() {
		assertTrue(projectsPage.clickProjectLink("My Project #9384").isLoaded("2"));
	}

}
