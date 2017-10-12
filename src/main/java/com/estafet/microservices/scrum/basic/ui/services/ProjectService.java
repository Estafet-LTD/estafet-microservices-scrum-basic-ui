package com.estafet.microservices.scrum.basic.ui.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.estafet.microservices.scrum.basic.ui.model.Project;
import com.estafet.microservices.scrum.basic.ui.model.ProjectBurndown;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProjectService {

	@Autowired
	private StoryService storyService;

	@Autowired
	private SprintService sprintService;
	
	@Autowired
	private RestTemplate restTemplate;

	@SuppressWarnings("rawtypes")
	public List<Project> getProjects() {
		List objects = restTemplate.getForObject(System.getenv("PROJECT_API_SERVICE_URI") + "/projects",
				List.class);
		List<Project> projects = new ArrayList<Project>();
		ObjectMapper mapper = new ObjectMapper();
		for (Object object : objects) {
			Project project = mapper.convertValue(object, new TypeReference<Project>() {
			});
			projects.add(project);
		}
		return projects;
	}

	public Project getProject(int projectId) {
		Project project = restTemplate.getForObject(System.getenv("PROJECT_API_SERVICE_URI") + "/project/{id}",
				Project.class, projectId);
		return project.addStories(storyService.getProjectStories(projectId))
				.addSprints(sprintService.getProjectSprints(projectId));
	}

	public Project createProject(Project project) {
		return restTemplate.postForObject(System.getenv("PROJECT_API_SERVICE_URI") + "/project", project,
				Project.class);
	}

	public ProjectBurndown getBurndown(int projectId) {
		ProjectBurndown burndown = restTemplate.getForObject(
				System.getenv("PROJECT_BURNDOWN_SERVICE_URI") + "/project/{id}/burndown", ProjectBurndown.class,
				projectId);
		burndown.setTitle(getProject(projectId).getTitle());
		return burndown;
	}

}
