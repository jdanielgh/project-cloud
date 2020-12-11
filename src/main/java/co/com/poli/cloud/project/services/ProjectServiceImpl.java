package co.com.poli.cloud.project.services;

import co.com.poli.cloud.project.domain.Project;
import co.com.poli.cloud.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) { this.projectRepository = projectRepository; }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project createProject(Project project) {
        Project projectBD = projectRepository.findByProjectIdentifier( project.getProjectIdentifier());
        if (projectBD == null ) {
            project.setState("Created");
            return projectRepository.save(project);
        }
        return null;
    }
}
