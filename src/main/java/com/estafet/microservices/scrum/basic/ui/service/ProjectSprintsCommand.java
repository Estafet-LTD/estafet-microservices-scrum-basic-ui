package com.estafet.microservices.scrum.basic.ui.service;

import java.util.List;

import com.estafet.microservices.scrum.basic.ui.model.Sprint;

public class ProjectSprintsCommand extends PollingCommand<List<Sprint>> {

	private SprintService sprintService;
	private Integer projectId;
	
	public ProjectSprintsCommand(Integer projectId, SprintService sprintService) {
		this.sprintService = sprintService;
		this.projectId = projectId;
	}

	@Override
	public boolean isReady(List<Sprint> result) {
		return !result.isEmpty();
	}

	@Override
	public List<Sprint> result() {
		return sprintService.getProjectSprints(projectId);
	}

}
