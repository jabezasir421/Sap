package com.example.sap.audit;

import com.example.sap.dto.AuditLogResponse;
import com.example.sap.multitenancy.TenantContext;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Transactional
    public void record(String username, String method, String path, int statusCode, long durationMs, String clientIp) {
        String tenantId = TenantContext.getTenant().orElse("system");
        AuditLog log = new AuditLog(tenantId, username, method, path, statusCode, durationMs, clientIp);
        auditLogRepository.save(log);
    }

    @Transactional(readOnly = true)
    public List<AuditLogResponse> listTenantLogs() {
        String tenantId = TenantContext.requireTenant();
        return auditLogRepository.findTop100ByTenantIdOrderByCreatedAtDesc(tenantId)
                .stream()
                .map(log -> new AuditLogResponse(
                        log.getId(),
                        log.getUsername(),
                        log.getMethod(),
                        log.getPath(),
                        log.getStatusCode(),
                        log.getDurationMs(),
                        log.getClientIp(),
                        log.getCreatedAt()))
                .toList();
    }
}
