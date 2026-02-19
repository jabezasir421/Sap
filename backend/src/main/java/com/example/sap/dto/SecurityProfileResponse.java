package com.example.sap.dto;

import java.util.List;

public record SecurityProfileResponse(
        String username,
        String tenantId,
        String authorizationLevel,
        List<String> authorities,
        List<String> segregationOfDutiesViolations
) {
}
