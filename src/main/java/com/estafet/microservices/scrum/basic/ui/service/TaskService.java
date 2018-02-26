package com.estafet.microservices.scrum.basic.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.estafet.microservices.scrum.basic.ui.config.UrlConstants;
import com.estafet.microservices.scrum.basic.ui.model.Story;
import com.estafet.microservices.scrum.basic.ui.model.Task;

import io.opentracing.Tracer;

@Service
public class TaskService {
	
	@Autowired
	private Tracer tracer;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private SprintService sprintService;
	
	public void claim(int taskId) {
		tracer.activeSpan().setTag("task.id", taskId);
		restTemplate.postForObject(UrlConstants.TASK_API_SERVICE_URI + "/task/{id}/claim", null,
				Task.class, taskId);
	}

	public void complete(int sprintId, int taskId) {
		tracer.activeSpan().setTag("sprint.id", sprintId);
		tracer.activeSpan().setTag("task.id", taskId);
		String lastSprintDay = sprintService.getLastSprintDay(sprintId);
		restTemplate.postForObject(UrlConstants.TASK_API_SERVICE_URI + "/task/{id}/complete", lastSprintDay,
				Task.class, taskId);
	}

	public Task getTask(int taskId) {
		tracer.activeSpan().setTag("task.id", taskId);
		return restTemplate.getForObject(UrlConstants.TASK_API_SERVICE_URI + "/task/{id}", Task.class,
				taskId);
	}

	public void updateRemainingTime(int taskId, Task task) {
		tracer.activeSpan().setTag("task.id", taskId);
		restTemplate.put(UrlConstants.TASK_API_SERVICE_URI + "/task/{id}/remainingHours", task, taskId);
	}

	public void addTask(int storyId, Task task) {
		tracer.activeSpan().setTag("story.id", storyId);
		restTemplate.postForObject(UrlConstants.TASK_API_SERVICE_URI + "/story/{id}/task", task, Story.class,
				storyId);
		tracer.activeSpan().setTag("task.id", task.getId());
	}

}
