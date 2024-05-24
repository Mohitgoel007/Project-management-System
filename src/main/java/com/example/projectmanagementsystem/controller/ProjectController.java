package com.example.projectmanagementsystem.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectmanagementsystem.exception.ProjectNotFoundException;
import com.example.projectmanagementsystem.models.Project;
import com.example.projectmanagementsystem.services.projectservices;

import io.swagger.annotations.*;

import java.util.List;

@RestController
@RequestMapping("/api/Project")
@Api(value="Project Management System", description="Operations pertaining to Projects in Project Management System")
public class ProjectController {
    
    @Autowired
    private projectservices projectService;

    @PostMapping
    @ApiOperation(value = "Create a new project", notes = "Provides an operation to create a new project")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Project created successfully"),
        @ApiResponse(code = 400, message = "Invalid input data")
    })
    public ResponseEntity<Project> createProject(@Valid @RequestBody Project project) {
        Project createdProject = projectService.createProject(project);
       return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
   }

    @GetMapping
    @ApiOperation(value = "Get all projects", notes = "Retrieves a list of all projects")
    @ApiResponse(code = 200, message = "Projects retrieved successfully", response = List.class)
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a project by ID", notes = "Retrieves a project by its ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Project found", response = Project.class),
        @ApiResponse(code = 404, message = "Project not found")
    })
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok().body(projectService.getProjectById(id));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a project", notes = "Updates an existing project by ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Project updated successfully", response = Project.class),
        @ApiResponse(code = 400, message = "Invalid input data"),
        @ApiResponse(code = 404, message = "Project not found")
    })
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @Valid @RequestBody Project projectDetails) {
        Project updatedProject = projectService.updateProject(id, projectDetails);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a project", notes = "Deletes a project by its ID")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Project deleted successfully"),
        @ApiResponse(code = 404, message = "Project not found")
    })
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<String> handleProjectNotFoundException(ProjectNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
