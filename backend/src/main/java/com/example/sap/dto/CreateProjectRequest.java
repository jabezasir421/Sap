package com.example.sap.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateProjectRequest(
        @NotBlank @Size(max = 140) String name,
        @Size(max = 1500) String description
) {
}
