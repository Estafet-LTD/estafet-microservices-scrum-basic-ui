package com.estafet.microservices.scrum.basic.ui.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.estafet.microservices.scrum.basic.ui.model.Sprint;
import com.estafet.microservices.scrum.lib.commons.wait.WaitUntilCommand;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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

	@SuppressWarnings("rawtypes")
	@Override
	public List<Sprint> result() {
		List objects = restTemplate.getForObject(System.getenv("SPRINT_API_SERVICE_URI") + "/project/{id}/sprints",
				List.class, projectId);
		List<Sprint> sprints = new ArrayList<Sprint>();
		ObjectMapper mapper = new ObjectMapper();
		for (Object object : objects) {
			Sprint sprint = mapper.convertValue(object, new TypeReference<Sprint>() {
			});
			sprints.add(sprint);
		}
		return sprints;
	}

}
