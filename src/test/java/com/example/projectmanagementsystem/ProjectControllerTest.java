package com.example.projectmanagementsystem;

import com.example.projectmanagementsystem.models.Project;
import com.example.projectmanagementsystem.services.projectservices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ProjectControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private projectservices projectService;

    private Project project1;
    private Project project2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        project1 = new Project();
        project1.setId(1L);
        project1.setName("Project 1");
        project1.setDescription("Description 1");
        project1.setStartDate(LocalDate.now());
        project1.setEndDate(LocalDate.now().plusDays(30));

        project2 = new Project();
        project2.setId(2L);
        project2.setName("Project 2");
        project2.setDescription("Description 2");
        project2.setStartDate(LocalDate.now().plusDays(10));
        project2.setEndDate(LocalDate.now().plusDays(20));

    }

    @Test
    void testCreateProject() throws Exception {

        // Project updatedProject = new Project();
        // updatedProject.setId(projectId);
        // updatedProject.setName("Updated Project 1");
        // updatedProject.setDescription("Updated Description 1");
        // updatedProject.setStartDate(LocalDate.now().plusDays(5));
        // updatedProject.setEndDate(LocalDate.now().plusDays(40));
        
        when(projectService.createProject(any(Project.class))).thenReturn(project2);

        mockMvc.perform(post("/api/Project")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(project2)))
               .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Project 2")))
                .andExpect(jsonPath("$.description", is("Description 2")))
                .andExpect(jsonPath("$.startDate", is(project2.getStartDate().toString())))
                .andExpect(jsonPath("$.endDate", is(project2.getEndDate().toString())));

        verify(projectService, times(1)).createProject(any(Project.class));
    }

    @Test
    void testGetAllProjects() throws Exception {
        List<Project> projects = Arrays.asList(project1, project2);

        when(projectService.getAllProjects()).thenReturn(projects);

        mockMvc.perform(get("/api/Project"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Project 1")))
                .andExpect(jsonPath("$[1].name", is("Project 2")));

        verify(projectService, times(1)).getAllProjects();
    }

    @Test
    void testGetProjectById() throws Exception {
        Long projectId = 1L;

        when(projectService.getProjectById(projectId)).thenReturn(project1);

        mockMvc.perform(get("/api/Project/{id}", projectId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(projectId.intValue())))
                .andExpect(jsonPath("$.name", is("Project 1")))
                .andExpect(jsonPath("$.description", is("Description 1")))
                .andExpect(jsonPath("$.startDate", is(project1.getStartDate().toString())))
                .andExpect(jsonPath("$.endDate", is(project1.getEndDate().toString())));

        verify(projectService, times(1)).getProjectById(projectId);
    }

    @Test
    void testUpdateProject() throws Exception {
        Long projectId = 1L;
        Project updatedProject = new Project();
        updatedProject.setId(projectId);
        updatedProject.setName("Updated Project 1");
        updatedProject.setDescription("Updated Description 1");
        updatedProject.setStartDate(LocalDate.now().plusDays(5));
        updatedProject.setEndDate(LocalDate.now().plusDays(40));

        when(projectService.updateProject(eq(projectId), any(Project.class))).thenReturn(updatedProject);

        mockMvc.perform(put("/api/Project/{id}", projectId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(updatedProject)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(projectId.intValue())))
                .andExpect(jsonPath("$.name", is("Updated Project 1")))
                .andExpect(jsonPath("$.description", is("Updated Description 1")))
                .andExpect(jsonPath("$.startDate", is(updatedProject.getStartDate().toString())))
                .andExpect(jsonPath("$.endDate", is(updatedProject.getEndDate().toString())));

        verify(projectService, times(1)).updateProject(eq(projectId), any(Project.class));
    }

    @Test
    void testDeleteProject() throws Exception {
        Long projectId = 1L;

        mockMvc.perform(delete("/api/Project/{id}", projectId))
                .andExpect(status().isNoContent());

        verify(projectService, times(1)).deleteProject(projectId);
    }
}

