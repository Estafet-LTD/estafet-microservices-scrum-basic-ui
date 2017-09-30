package com.microservices.scrum.basic.ui.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.scrum.basic.ui.messages.StartSprint;
import com.microservices.scrum.basic.ui.model.Sprint;
import com.microservices.scrum.basic.ui.model.SprintBurndown;
import com.microservices.scrum.basic.ui.model.Story;
import com.microservices.scrum.basic.ui.model.Task;

@Service
public class SprintService {

	@Autowired
	private StoryService storyService;

	public Sprint getSprint(int projectId, int sprintId) {
		Sprint sprint = new RestTemplate().getForObject(System.getenv("SPRINT_API_SERVICE_URI") + "/sprint/{id}",
				Sprint.class, sprintId);
		sprint.setProjectId(projectId);
		List<Story> stories = storyService.getProjectStories(projectId);
		return sprint.addStories(stories);
	}

	@SuppressWarnings({ "rawtypes" })
	public List<Sprint> getProjectSprints(int projectId) {
		List objects =  new RestTemplate().getForObject(System.getenv("SPRINT_API_SERVICE_URI") + "/project/{id}/sprints",
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
		storyService.addStoryToSprint(sprintId, storyId);
		Sprint sprint = getSprint(projectId, sprintId);
		return sprint;
	}

	public void startSprint(int projectId, StartSprint startSprint) {
		startSprint.setProjectId(projectId);
		new RestTemplate().postForObject(System.getenv("SPRINT_API_SERVICE_URI") + "/start-sprint", startSprint,
				Sprint.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getSprintDays(int sprintId, Task task) {
		List<String> days = new RestTemplate().getForObject(System.getenv("SPRINT_API_SERVICE_URI") + "/sprint/{id}/days",
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
	
	public String getSprintDay(int sprintId) {
		return new RestTemplate().getForObject(System.getenv("SPRINT_API_SERVICE_URI") + "/sprint/{id}/day",
				String.class, sprintId);
	}
	
	public SprintBurndown getSprintBurndown(int sprintId) {
		return new RestTemplate().getForObject(System.getenv("SPRINT_BURNDOWN_SERVICE_URI") + "/sprint/{id}/burndown",
				SprintBurndown.class, sprintId);
	}

}
