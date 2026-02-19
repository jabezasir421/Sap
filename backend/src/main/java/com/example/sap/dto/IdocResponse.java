package com.example.sap.dto;

import com.example.sap.integration.IdocDirection;
import java.time.Instant;
import java.util.UUID;

public record IdocResponse(
        UUID id,
        String messageType,
        IdocDirection direction,
        String status,
        String payload,
        Instant createdAt
) {
}
