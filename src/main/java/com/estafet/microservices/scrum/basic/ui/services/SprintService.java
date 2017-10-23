package com.estafet.microservices.scrum.basic.ui.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.estafet.microservices.scrum.basic.ui.messages.StartSprint;
import com.estafet.microservices.scrum.basic.ui.model.Sprint;
import com.estafet.microservices.scrum.basic.ui.model.SprintBurndown;
import com.estafet.microservices.scrum.basic.ui.model.Story;
import com.estafet.microservices.scrum.basic.ui.model.Task;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.opentracing.Tracer;

@Service
public class SprintService {

	@Autowired
	private Tracer tracer;

	@Autowired
	private StoryService storyService;

	@Autowired
	private RestTemplate restTemplate;

	public Sprint getSprint(int projectId, int sprintId) {
		tracer.activeSpan().setTag("project.id", projectId);
		tracer.activeSpan().setTag("sprint.id", sprintId);
		Sprint sprint = restTemplate.getForObject(System.getenv("SPRINT_API_SERVICE_URI") + "/sprint/{id}",
				Sprint.class, sprintId);
		sprint.setProjectId(projectId);
		List<Story> stories = storyService.getProjectStories(projectId);
		return sprint.addStories(stories);
	}

	@SuppressWarnings({ "rawtypes" })
	public List<Sprint> getProjectSprints(int projectId) {
		tracer.activeSpan().setTag("project.id", projectId);
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

	public Sprint addStoryToSprint(int projectId, int sprintId, int storyId) {
		tracer.activeSpan().setTag("project.id", projectId);
		tracer.activeSpan().setTag("sprint.id", sprintId);
		tracer.activeSpan().setTag("story.id", storyId);
		storyService.addStoryToSprint(sprintId, storyId);
		Sprint sprint = getSprint(projectId, sprintId);
		return sprint;
	}

	public void startSprint(int projectId, StartSprint startSprint) {
		tracer.activeSpan().setTag("project.id", projectId);
		startSprint.setProjectId(projectId);
		Sprint sprint = restTemplate.postForObject(System.getenv("SPRINT_API_SERVICE_URI") + "/start-sprint",
				startSprint, Sprint.class);
		tracer.activeSpan().setTag("sprint.id", sprint.getId());
	}

	@SuppressWarnings("unchecked")
	public List<String> getSprintDays(int sprintId, Task task) {
		tracer.activeSpan().setTag("sprint.id", sprintId);
		List<String> days = restTemplate.getForObject(System.getenv("SPRINT_API_SERVICE_URI") + "/sprint/{id}/days",
				List.class, sprintId);
		Iterator<String> iterator = days.iterator();
		while (iterator.hasNext()) {
			if (task.getRemainingUpdated().equals(iterator.next())) {
				return days;
			} else {
				iterator.remove();
			}
		}
		return days;
	}

	@SuppressWarnings("unchecked")
	public String getLastSprintDay(int sprintId) {
		tracer.activeSpan().setTag("sprint.id", sprintId);
		List<String> days = restTemplate.getForObject(System.getenv("SPRINT_API_SERVICE_URI") + "/sprint/{id}/days",
				List.class, sprintId);
		return days.get(days.size() - 1);
	}

	public String getSprintDay(int sprintId) {
		tracer.activeSpan().setTag("sprint.id", sprintId);
		return restTemplate.getForObject(System.getenv("SPRINT_API_SERVICE_URI") + "/sprint/{id}/day", String.class,
				sprintId);
	}

	public SprintBurndown getSprintBurndown(int sprintId) {
		tracer.activeSpan().setTag("sprint.id", sprintId);
		SprintBurndown burndown = restTemplate.getForObject(System.getenv("SPRINT_BURNDOWN_SERVICE_URI") + "/sprint/{id}/burndown",
				SprintBurndown.class, sprintId);
		System.out.println(burndown.toJSON());
		return burndown;
	}

}
