package com.estafet.microservices.scrum.basic.ui.selenium.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import com.estafet.microservices.scrum.basic.ui.selenium.pages.HomePage;

public class ITHomePageTest {

	HomePage homePage;
	
	@Before
	public void before() throws Exception {
		homePage = new HomePage();
	}

	@After
	public void after() throws Exception {
		homePage.close();
	}

	@Test
	public void testLinkToProjects() {
		assertTrue(homePage.projectsPage().isLoaded());		
	}

}
