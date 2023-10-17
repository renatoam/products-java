package com.example.todoapp.project;

import com.example.todoapp.common.BaseController;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(BaseController.API_VERSION + "/projects")
public class ProjectController extends BaseController {
    @Autowired
    private ProjectRepository repository;

    @GetMapping
    public ResponseEntity getProjects() {
        List<Project> projects = this.repository.findAll();

        if (projects.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CREATED).body("There is no project created yet.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(projects);
    }

    @PostMapping
    public ResponseEntity createProject(@RequestBody @Valid CreateProjectDTO createProjectDTO) {
        Optional<Project> project = this.repository.findByName(createProjectDTO.name());

        if (!project.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is already a project with this name.");
        }

        Project newProject = new Project();
        BeanUtils.copyProperties(createProjectDTO, newProject);
        this.repository.save(newProject);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProject);
    }

    @PutMapping
    public ResponseEntity updateProject(@RequestBody @Valid UpdateProjectDTO updateProjectDTO) {
        Optional<Project> project = this.repository.findById(updateProjectDTO.id());

        if (project.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no such project.");
        }

        BeanUtils.copyProperties(updateProjectDTO, project.get());
        this.repository.save(project.get());
        return ResponseEntity.status(HttpStatus.OK).body(project.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProject(@PathVariable UUID id) {
        Optional<Project> project = this.repository.findById(id);

        if (project.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no such project.");
        }

        this.repository.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Project successfully deleted.");
    }
}
