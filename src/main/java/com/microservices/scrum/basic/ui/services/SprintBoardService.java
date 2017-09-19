package com.microservices.scrum.basic.ui.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservices.scrum.basic.ui.model.SprintBoard;

@Service
public class SprintBoardService {

	@Autowired
	private TaskService taskService;
	
	public SprintBoard getSprintBoard(int sprintId) {
		return new RestTemplate().getForObject("http://localhost:8080/sprint-board-api/sprint/{id}/board",
				SprintBoard.class, sprintId);
	}
	
	public SprintBoard claim(int sprintId, int taskId) {
		taskService.claim(taskId);
		return getSprintBoard(sprintId);
	}
	
	public SprintBoard complete(int sprintId, int taskId) {
		taskService.complete(taskId);
		return getSprintBoard(sprintId);
	}

}
