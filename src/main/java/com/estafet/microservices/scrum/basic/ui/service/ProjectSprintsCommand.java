package com.estafet.microservices.scrum.basic.ui.service;

import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.estafet.microservices.scrum.basic.ui.model.Sprint;
import com.estafet.microservices.scrum.lib.commons.rest.RestHelper;
import com.estafet.microservices.scrum.lib.commons.wait.WaitUntilCommand;

public class ProjectSprintsCommand extends WaitUntilCommand<List<Sprint>> {

	private Integer projectId;
	private RestTemplate restTemplate;

	public ProjectSprintsCommand(Integer projectId, RestTemplate restTemplate) {
		this.projectId = projectId;
		this.restTemplate = restTemplate;
	}

	@Override
	public boolean isReady(List<Sprint> result) {
		return !result.isEmpty();
	}

	@Override
	public List<Sprint> result() {
		return RestHelper.getRestQuery(restTemplate, System.getenv("SPRINT_API_SERVICE_URI") + "/project/{id}/sprints",
				Sprint.class, projectId);
	}

}
