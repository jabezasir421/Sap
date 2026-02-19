package com.example.sap.dto;

import java.time.Instant;
import java.util.UUID;

public record AuditLogResponse(
        UUID id,
        String username,
        String method,
        String path,
        int statusCode,
        long durationMs,
        String clientIp,
        Instant createdAt
) {
}
