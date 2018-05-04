package com.estafet.microservices.scrum.basic.ui.selenium.tests;

import static org.junit.Assert.*;

import static org.hamcrest.core.Is.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.estafet.microservices.scrum.basic.ui.selenium.pages.SprintBoardPage;
import com.estafet.microservices.scrum.basic.ui.selenium.pages.SprintPage;
import com.estafet.microservices.scrum.basic.ui.selenium.pages.StoryPage;
import com.github.tomakehurst.wiremock.WireMockServer;

public class ITSprintBoardPageTest {

	WireMockServer wireMockServer = new WireMockServer();
	SprintBoardPage sprintBoardPage;
	
	@Before
	public void before() {
		wireMockServer.start();
		sprintBoardPage = new SprintBoardPage("1","2");
	}

	@After
	public void after() {
		wireMockServer.stop();
		sprintBoardPage.close();
	}

	@Test
	public void testViewToDoTasks() {
		assertThat(sprintBoardPage.todoTasks().size(), is(3));
		assertThat(sprintBoardPage.todoTasks().get(0).name(), is("Task #289"));
		assertThat(sprintBoardPage.todoTasks().get(1).name(), is("Task #6999"));
		assertThat(sprintBoardPage.todoTasks().get(2).name(), is("Task #9305"));
	}
	
	@Test
	public void testName() {
		assertThat(sprintBoardPage.getName(), is("Sprint #1"));
	}
	
}
