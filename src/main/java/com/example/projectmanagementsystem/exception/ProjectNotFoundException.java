package com.example.projectmanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProjectNotFoundException  extends RuntimeException{
    public ProjectNotFoundException(String string) {
        super("Project not found with id: " + string);
    }
    public ProjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
