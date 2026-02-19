package com.example.sap.integration;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdocRepository extends JpaRepository<Idoc, UUID> {

    List<Idoc> findAllByTenantIdOrderByCreatedAtDesc(String tenantId);
}
