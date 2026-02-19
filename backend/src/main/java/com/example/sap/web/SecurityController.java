package com.example.sap.web;

import com.example.sap.dto.SecurityProfileResponse;
import com.example.sap.security.SecurityProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/security")
@RequiredArgsConstructor
public class SecurityController {

    private final SecurityProfileService securityProfileService;

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public SecurityProfileResponse me(Authentication authentication) {
        return securityProfileService.build(authentication);
    }
}
