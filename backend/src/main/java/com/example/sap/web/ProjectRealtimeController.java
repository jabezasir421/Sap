package com.example.sap.web;

import com.example.sap.multitenancy.TenantContext;
import com.example.sap.service.ProjectStreamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/v1/stream")
@RequiredArgsConstructor
public class ProjectRealtimeController {

    private final ProjectStreamService projectStreamService;

    @GetMapping(path = "/projects", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','INTEGRATION','FI_ACCOUNTANT','CO_CONTROLLER','MM_MANAGER','PP_PLANNER','SD_MANAGER','HCM_ADMIN')")
    public SseEmitter streamProjects() {
        return projectStreamService.subscribe(TenantContext.requireTenant());
    }
}
