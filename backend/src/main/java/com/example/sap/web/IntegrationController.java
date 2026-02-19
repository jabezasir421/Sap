package com.example.sap.web;

import com.example.sap.dto.CreateIdocRequest;
import com.example.sap.dto.IdocResponse;
import com.example.sap.integration.IntegrationService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/integration/idocs")
@RequiredArgsConstructor
public class IntegrationController {

    private final IntegrationService integrationService;

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','INTEGRATION','AUDITOR')")
    public List<IdocResponse> listIdocs() {
        return integrationService.listIdocs();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','INTEGRATION')")
    public IdocResponse createIdoc(@RequestBody @Valid CreateIdocRequest request) {
        return integrationService.createIdoc(request);
    }
}
