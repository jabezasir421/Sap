package com.example.sap.web;

import com.example.sap.dto.ModuleSummaryResponse;
import com.example.sap.service.ErpModuleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/modules")
@RequiredArgsConstructor
public class ErpModuleController {

    private final ErpModuleService erpModuleService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<ModuleSummaryResponse> listModules() {
        return erpModuleService.listModules();
    }
}
