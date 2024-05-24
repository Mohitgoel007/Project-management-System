package com.example.projectmanagementsystem.services;

import java.util.List;
// import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmanagementsystem.exception.*;
import com.example.projectmanagementsystem.models.Project;
import com.example.projectmanagementsystem.repositories.projectrepository;

@Service
public class projectservices {
    
    @Autowired
    private projectrepository projectRepository;

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id) throws Exception {
        return projectRepository.findById(id).orElseThrow(() -> throwException(String.valueOf(id)));
    }

    public Project updateProject(Long id, Project projectDetails) {
        Project project = projectRepository.findById(id).orElseThrow(() -> throwException(String.valueOf(id)));
        project.setName(projectDetails.getName());
        project.setDescription(projectDetails.getDescription());
        project.setStartDate(projectDetails.getStartDate());
        project.setEndDate(projectDetails.getEndDate());
        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> throwException(String.valueOf(id)));
            projectRepository.delete(project);
    }
    private ProjectNotFoundException throwException(String value) {
        throw new ProjectNotFoundException(value);
    }
}
