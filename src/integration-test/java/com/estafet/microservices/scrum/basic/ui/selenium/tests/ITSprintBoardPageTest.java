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
		sprintBoardPage = new SprintBoardPage("1","1");
	}

	@After
	public void after() {
		wireMockServer.stop();
		sprintBoardPage.close();
	}

	@Test
	public void testViewToDoTasks() {
		assertThat(sprintBoardPage.todoTasks().size(), is(2));
		assertThat(sprintBoardPage.todoTasks().get(0).name(), is("Task #6999"));
		assertThat(sprintBoardPage.todoTasks().get(1).name(), is("Task #9305"));
	}
	
	@Test
	public void testViewInProgressTasks() {
		assertThat(sprintBoardPage.inProgressTasks().size(), is(1));
		assertThat(sprintBoardPage.inProgressTasks().get(0).name(), is("Task #4547"));
	}
	
	@Test
	public void testClickInProgressUpdateHours() {
		assertTrue(sprintBoardPage.inProgressTasks().get(0).updateHours().isLoaded("1", "1", "2"));
	}
	
	@Test
	public void testViewCompletedTasks() {
		assertThat(sprintBoardPage.completedTasks().size(), is(2));
		assertThat(sprintBoardPage.completedTasks().get(0).name(), is("Task #289"));
		assertThat(sprintBoardPage.completedTasks().get(1).name(), is("Task #6329"));
	}
	
	@Test
	public void testName() {
		assertThat(sprintBoardPage.getName(), is("Sprint #1"));
	}
	
}
