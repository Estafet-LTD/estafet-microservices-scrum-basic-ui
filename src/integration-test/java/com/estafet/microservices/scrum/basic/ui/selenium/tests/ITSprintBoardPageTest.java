package com.estafet.microservices.scrum.basic.ui.selenium.tests;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.estafet.microservices.scrum.lib.selenium.pages.sprint.SprintBoardPage;

public class ITSprintBoardPageTest {

	SprintBoardPage sprintBoardPage;
	
	@Before
	public void before() {
		sprintBoardPage = new SprintBoardPage("2","1");
	}

	@After
	public void after() {
		sprintBoardPage.close();
	}

	@Test
	public void testViewToDoTasks() {
		assertThat(sprintBoardPage.getTodoTasks().size(), is(1));
		assertThat(sprintBoardPage.getTodoTasks().get(0).getTaskTitle(), is("this is a task"));
	}
	
	@Test
	public void testViewInProgressTasks() {
		assertThat(sprintBoardPage.getInProgressTasks().size(), is(2));
		assertThat(sprintBoardPage.getInProgressTasks().get(0).getTaskTitle(), is("hghghg"));
		assertThat(sprintBoardPage.getInProgressTasks().get(0).getHours(), is("10 Hours"));
		assertThat(sprintBoardPage.getInProgressTasks().get(1).getTaskTitle(), is("qwwqwqwaa"));
		assertThat(sprintBoardPage.getInProgressTasks().get(1).getHours(), is("3 Hours"));
	}
	
	@Test
	public void testClickInProgressUpdateHours() {
		assertTrue(sprintBoardPage.getInProgressTasks().get(0).clickHoursLink().isLoaded("2", "2", "5"));
	}
	
	@Test
	public void testViewCompletedTasks() {
		assertThat(sprintBoardPage.getCompletedTasks().size(), is(1));
		assertThat(sprintBoardPage.getCompletedTasks().get(0).getTaskTitle(), is("this is a task"));
	}
	
	@Test
	public void testName() {
		assertThat(sprintBoardPage.getName(), is("Sprint #2"));
	}
	
	@Test
	public void testClickProjectsBreadCrumb() {
		assertTrue(sprintBoardPage.clickProjectsBreadCrumbLink().isLoaded());
	}
	
	@Test
	public void testClickProjectBreadCrumb() {
		assertTrue(sprintBoardPage.clickProjectBreadCrumbLink().isLoaded("2"));
	}
	
	@Test
	public void testClickSprintBreadCrumb() {
		assertTrue(sprintBoardPage.clickSprintBreadCrumbLink().isLoaded("2", "1"));
	}
	
}
