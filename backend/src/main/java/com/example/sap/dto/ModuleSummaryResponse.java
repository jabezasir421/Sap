package com.example.sap.dto;

public record ModuleSummaryResponse(
        String module,
        String status,
        String reportingScope,
        String description,
        String criticalNote,
        java.util.List<String> capabilities
) {
}
