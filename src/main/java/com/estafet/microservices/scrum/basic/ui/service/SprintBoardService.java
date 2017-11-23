package com.estafet.microservices.scrum.basic.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.estafet.microservices.scrum.basic.ui.model.SprintBoard;

import io.opentracing.Tracer;

@Service
public class SprintBoardService {

	@Autowired
	private Tracer tracer;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private RestTemplate restTemplate;

	@Retryable(maxAttempts = 3, backoff = @Backoff(delay=200))
	public SprintBoard getSprintBoard(int sprintId) {
		tracer.activeSpan().setTag("sprint.id", sprintId);
		return restTemplate.getForObject(System.getenv("SPRINT_BOARD_API_SERVICE_URI") + "/sprint/{id}/board",
				SprintBoard.class, sprintId);
	}

	public SprintBoard claim(int sprintId, int taskId) {
		tracer.activeSpan().setTag("task.id", taskId);
		taskService.claim(taskId);
		return getSprintBoard(sprintId);
	}

	public SprintBoard complete(int sprintId, int taskId) {
		tracer.activeSpan().setTag("task.id", taskId);
		taskService.complete(sprintId, taskId);
		return getSprintBoard(sprintId);
	}
	
	

}
