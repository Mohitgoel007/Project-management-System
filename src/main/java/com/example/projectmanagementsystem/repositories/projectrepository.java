package com.example.projectmanagementsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projectmanagementsystem.models.Project;

public interface projectrepository extends JpaRepository<Project, Long>{
    
}
