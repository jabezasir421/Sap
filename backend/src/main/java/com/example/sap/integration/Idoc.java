package com.example.sap.integration;

import com.example.sap.domain.TenantScopedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "idocs")
public class Idoc extends TenantScopedEntity {

    @Id
    private UUID id;

    @Column(name = "message_type", nullable = false, length = 80)
    private String messageType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private IdocDirection direction;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String payload;

    protected Idoc() {
    }

    public Idoc(String messageType, IdocDirection direction, String payload) {
        this.id = UUID.randomUUID();
        this.messageType = messageType;
        this.direction = direction;
        this.status = "RECEIVED";
        this.payload = payload;
    }

    public UUID getId() {
        return id;
    }

    public String getMessageType() {
        return messageType;
    }

    public IdocDirection getDirection() {
        return direction;
    }

    public String getStatus() {
        return status;
    }

    public String getPayload() {
        return payload;
    }
}
