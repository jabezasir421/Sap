package com.example.sap.web;

import com.example.sap.audit.AuditLogService;
import com.example.sap.dto.AuditLogResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/audit-logs")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','AUDITOR')")
    public List<AuditLogResponse> listAuditLogs() {
        return auditLogService.listTenantLogs();
    }
}
