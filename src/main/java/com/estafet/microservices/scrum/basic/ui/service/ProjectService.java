package com.estafet.microservices.scrum.basic.ui.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.estafet.microservices.scrum.basic.ui.model.Project;
import com.estafet.microservices.scrum.basic.ui.model.ProjectBurndown;
import com.estafet.microservices.scrum.basic.ui.model.Sprint;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.opentracing.Tracer;

@Service
public class ProjectService {
	
	@Autowired
	private Tracer tracer;
	
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
		tracer.activeSpan().setTag("project.id", projectId);
		Project project = restTemplate.getForObject(System.getenv("PROJECT_API_SERVICE_URI") + "/project/{id}",
				Project.class, projectId);
		return project.addStories(storyService.getProjectStories(projectId))
					  .addSprints(new ProjectSprintsCommand(projectId, restTemplate).execute());
	}

	public Project createProject(Project project) {
		tracer.activeSpan().setTag("project.id", project.getId());
		project = restTemplate.postForObject(System.getenv("PROJECT_API_SERVICE_URI") + "/project", project,
				Project.class);
		return project;
	}

	public ProjectBurndown getBurndown(int projectId) {
		tracer.activeSpan().setTag("project.id", projectId);
		ProjectBurndown burndown = restTemplate.getForObject(
				System.getenv("PROJECT_BURNDOWN_SERVICE_URI") + "/project/{id}/burndown", ProjectBurndown.class,
				projectId);
		return burndown;
	}

}
