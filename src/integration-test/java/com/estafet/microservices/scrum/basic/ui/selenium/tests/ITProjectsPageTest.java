package com.estafet.microservices.scrum.basic.ui.selenium.tests;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.estafet.microservices.scrum.basic.ui.selenium.pages.ProjectsPage;
import com.github.tomakehurst.wiremock.WireMockServer;

public class ITProjectsPageTest {

	WireMockServer wireMockServer = new WireMockServer();
	ProjectsPage projectsPage;
	
	@Before
	public void before() {
		wireMockServer.start();
		stubFor(get(urlEqualTo("/project-api/projects")).willReturn(aResponse().withBody(
				"[\r\n" + 
				"    {\r\n" + 
				"        \"id\": 2,\r\n" + 
				"        \"title\": \"My Project #9384\",\r\n" + 
				"        \"noSprints\": 5,\r\n" + 
				"        \"sprintLengthDays\": 5\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"        \"id\": 3,\r\n" + 
				"        \"title\": \"My Project #1749\",\r\n" + 
				"        \"noSprints\": 5,\r\n" + 
				"        \"sprintLengthDays\": 5\r\n" + 
				"    }\r\n" + 
				"]")));
		projectsPage = new ProjectsPage();
	}

	@After
	public void after() {
		wireMockServer.stop();
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
		assertTrue(projectsPage.newProjectPage().isLoaded());
	}

	@Test
	public void testClickProject() {
		assertTrue(projectsPage.projectPage("My Project #9384").isLoaded("1"));
	}

}
