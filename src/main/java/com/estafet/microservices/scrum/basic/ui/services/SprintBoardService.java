package com.estafet.microservices.scrum.basic.ui.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.estafet.microservices.scrum.basic.ui.model.SprintBoard;

@Service
public class SprintBoardService {

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private RestTemplate restTemplate;

	public SprintBoard getSprintBoard(int sprintId) {
		return restTemplate.getForObject(System.getenv("SPRINT_BOARD_API_SERVICE_URI") + "/sprint/{id}/board",
				SprintBoard.class, sprintId);
	}

	public SprintBoard claim(int sprintId, int taskId) {
		taskService.claim(taskId);
		return getSprintBoard(sprintId);
	}

	public SprintBoard complete(int sprintId, int taskId) {
		taskService.complete(sprintId, taskId);
		return getSprintBoard(sprintId);
	}
	
	

}
