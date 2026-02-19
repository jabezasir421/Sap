package com.example.sap.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "projects")
public class Project extends TenantScopedEntity {

    @Id
    private UUID id;

    @Column(nullable = false, length = 140)
    private String name;

    @Column(length = 1500)
    private String description;

    protected Project() {
    }

    public Project(String name, String description) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
