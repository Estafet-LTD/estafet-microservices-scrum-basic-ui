package com.microservices.scrum.basic.ui.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.scrum.basic.ui.model.Project;

@Service
public class ProjectService {

	@Autowired
	private StoryService storyService;

	@SuppressWarnings("rawtypes")
	public List<Project> getProjects() {
		List objects = new RestTemplate().getForObject("http://localhost:8080/project-api/projects", List.class);
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
		System.out.println("project before :" + projectId);
		Project project = new RestTemplate().getForObject("http://localhost:8080/project-api/project/{id}",
				Project.class, projectId);
		System.out.println("project after :" + project.getId());
		return project.addStories(storyService.getProjectStories(projectId));
	}

	public Project createProject(Project project) {
		System.out.println("this is the title - " + project.getTitle());
		return new RestTemplate().postForObject("http://localhost:8080/project-api/project", project, Project.class);
	}

}
