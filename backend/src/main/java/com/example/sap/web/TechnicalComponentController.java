package com.example.sap.web;

import com.example.sap.dto.TechnicalComponentResponse;
import com.example.sap.technical.TechnicalComponentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/technical/components")
@RequiredArgsConstructor
public class TechnicalComponentController {

    private final TechnicalComponentService technicalComponentService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<TechnicalComponentResponse> listComponents() {
        return technicalComponentService.listComponents();
    }
}
