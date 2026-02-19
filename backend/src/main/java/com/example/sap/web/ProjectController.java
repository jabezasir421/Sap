package com.example.sap.web;

import com.example.sap.dto.CreateProjectRequest;
import com.example.sap.dto.ProjectResponse;
import com.example.sap.service.ProjectService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','AUDITOR','FI_ACCOUNTANT','CO_CONTROLLER','MM_MANAGER','PP_PLANNER','SD_MANAGER','HCM_ADMIN')")
    public List<ProjectResponse> listProjects() {
        return projectService.listProjects();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','FI_ACCOUNTANT','MM_MANAGER','PP_PLANNER','SD_MANAGER')")
    public ProjectResponse createProject(@RequestBody @Valid CreateProjectRequest request) {
        return projectService.createProject(request);
    }
}
