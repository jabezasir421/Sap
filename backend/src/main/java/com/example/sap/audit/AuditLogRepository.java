package com.example.sap.audit;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, java.util.UUID> {

    List<AuditLog> findTop100ByTenantIdOrderByCreatedAtDesc(String tenantId);
}
