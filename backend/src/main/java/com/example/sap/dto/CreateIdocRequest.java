package com.example.sap.dto;

import com.example.sap.integration.IdocDirection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateIdocRequest(
        @NotBlank @Size(max = 80) String messageType,
        @NotNull IdocDirection direction,
        @NotBlank String payload
) {
}
