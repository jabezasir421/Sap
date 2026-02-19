package com.example.sap.repository;

import com.example.sap.domain.Project;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, UUID> {

    List<Project> findAllByTenantIdOrderByCreatedAtDesc(String tenantId);

    Optional<Project> findByIdAndTenantId(UUID id, String tenantId);
}
