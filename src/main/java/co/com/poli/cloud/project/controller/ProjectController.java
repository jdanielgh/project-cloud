package co.com.poli.cloud.project.controller;

import co.com.poli.cloud.project.domain.Project;
import co.com.poli.cloud.project.model.ErrorMessage;
import co.com.poli.cloud.project.services.ProjectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/projects")
public class ProjectController {

    private ProjectService projectService;

    public ProjectController(ProjectService projectService) { this.projectService = projectService; }

    @GetMapping
    public ResponseEntity<List<Project>> listProject() {
        List<Project> projects = projectService.getAllProjects();
        if (projects == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(projects);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@Valid @RequestBody Project project, BindingResult result) {
        if ( result.hasErrors() ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Project projectBD = projectService.createProject(project);
        if ( projectBD == null ) {
            project.setState("No Created");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(project);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(projectBD);
    }

    private String formatMessage(BindingResult result) {
        List<Map<String, String>> messages = result.getFieldErrors().stream()
                .map( err -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(messages).build();
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
