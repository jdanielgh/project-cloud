package co.com.poli.cloud.project.services;

import co.com.poli.cloud.project.domain.Project;

import java.util.List;

public interface ProjectService {
    List<Project> getAllProjects();
    Project createProject(Project project);
}
