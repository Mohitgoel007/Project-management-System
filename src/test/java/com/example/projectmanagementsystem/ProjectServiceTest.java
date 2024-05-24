package com.example.projectmanagementsystem;

import com.example.projectmanagementsystem.exception.*;
import com.example.projectmanagementsystem.models.Project;
import com.example.projectmanagementsystem.repositories.projectrepository;
import com.example.projectmanagementsystem.services.projectservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the ProjectService implementation.
 * This class tests the functionality of the ProjectServiceImpl class
 * using Mockito for mocking the ProjectRepository.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProjectServiceTest {

    @Mock
    private projectrepository projectRepository;

    @InjectMocks
    private projectservices projectService;

    
    private Project project1;
    private Project project2;

    @BeforeEach
    void setUp() {
        project1 = new Project();
        project1.setName("Project 1");
        project1.setDescription("Description 1");
        project1.setStartDate(LocalDate.now());
        project1.setEndDate(LocalDate.now().plusDays(30));

        project2 = new Project();
        project2.setName("Project 2");
        project2.setDescription("Description 2");
        project2.setStartDate(LocalDate.now().plusDays(10));
        project2.setEndDate(LocalDate.now().plusDays(40));
    }

    @Test
    public void testGetProjectById_Success() throws Exception {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project1));

        Project foundProject = projectService.getProjectById(1L);

        assertNotNull(foundProject);
        assertEquals(project1.getId(), foundProject.getId());
        assertEquals(project1.getName(), foundProject.getName());
    }

    @Test
    public void testGetProjectById_NotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProjectNotFoundException.class, () -> {
            projectService.getProjectById(1L);
        });
    }

    @Test
    public void testCreateProject() {
        when(projectRepository.save(any(Project.class))).thenReturn(project1);

        Project createdProject = projectService.createProject(project1);

        assertNotNull(createdProject);
        assertEquals(project1.getId(), createdProject.getId());
        assertEquals(project1.getName(), createdProject.getName());
        verify(projectRepository, times(1)).save(project1);
    }

    @Test
    public void testGetAllProjects() {
        when(projectRepository.findAll()).thenReturn(Arrays.asList(project1, project2));

        List<Project> projects = projectService.getAllProjects();

        assertNotNull(projects);
        assertEquals(2, projects.size());
        assertEquals(project1.getId(), projects.get(0).getId());
        assertEquals(project2.getId(), projects.get(1).getId());
        verify(projectRepository, times(1)).findAll();
    }

    @Test
public void testDeleteProject_Success() {
    when(projectRepository.findById(1L)).thenReturn(Optional.of(project1));
    doNothing().when(projectRepository).delete(project1);

    projectService.deleteProject(1L);

    verify(projectRepository, times(1)).findById(1L);
    verify(projectRepository, times(1)).delete(project1);
}

@Test
public void testDeleteProject_NotFound() {
    when(projectRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(ProjectNotFoundException.class, () -> {
        projectService.deleteProject(1L);
    });

    verify(projectRepository, times(1)).findById(1L);
    verify(projectRepository, times(0)).delete(any(Project.class));
}
}
