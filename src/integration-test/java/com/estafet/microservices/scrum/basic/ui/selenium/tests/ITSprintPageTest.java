package com.estafet.microservices.scrum.basic.ui.selenium.tests;

import static org.junit.Assert.*;

import static org.hamcrest.core.Is.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.estafet.microservices.scrum.basic.ui.selenium.pages.ProjectPage;
import com.estafet.microservices.scrum.basic.ui.selenium.pages.SprintPage;
import com.github.tomakehurst.wiremock.WireMockServer;

public class ITSprintPageTest {

	WireMockServer wireMockServer = new WireMockServer();
	SprintPage sprintPage;
	
	@Before
	public void before() {
		wireMockServer.start();
		sprintPage = new SprintPage("1", "1");
	}

	@After
	public void after() {
		wireMockServer.stop();
		sprintPage.close();
	}

	@Test
	public void testViewSprintStories() {
		assertThat(sprintPage.getSprintStories().size(), is(2));
		assertThat(sprintPage.getSprintStories().get(0), is("Story #9869"));
		assertThat(sprintPage.getSprintStories().get(3), is("Story #4249"));
	}


}
