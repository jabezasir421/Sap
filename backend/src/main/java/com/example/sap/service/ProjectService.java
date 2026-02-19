package com.example.sap.service;

import com.example.sap.domain.Project;
import com.example.sap.dto.CreateProjectRequest;
import com.example.sap.dto.ProjectResponse;
import com.example.sap.multitenancy.TenantContext;
import com.example.sap.repository.ProjectRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectStreamService projectStreamService;

    @Transactional(readOnly = true)
    public List<ProjectResponse> listProjects() {
        String tenantId = TenantContext.requireTenant();
        return projectRepository.findAllByTenantIdOrderByCreatedAtDesc(tenantId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public ProjectResponse createProject(CreateProjectRequest request) {
        String tenantId = TenantContext.requireTenant();
        Project project = new Project(request.name(), request.description());
        Project saved = projectRepository.save(project);
        ProjectResponse response = toResponse(saved);
        projectStreamService.publishCreated(tenantId, response);
        return response;
    }

    private ProjectResponse toResponse(Project project) {
        return new ProjectResponse(project.getId(), project.getName(), project.getDescription(), project.getCreatedAt());
    }
}
