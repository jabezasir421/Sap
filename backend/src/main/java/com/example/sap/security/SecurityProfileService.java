package com.example.sap.security;

import com.example.sap.dto.SecurityProfileResponse;
import com.example.sap.multitenancy.TenantContext;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class SecurityProfileService {

    public SecurityProfileResponse build(Authentication authentication) {
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority -> authority.startsWith("ROLE_"))
                .sorted()
                .toList();

        return new SecurityProfileResponse(
                authentication.getName(),
                TenantContext.getTenant().orElse(""),
                authorizationLevel(roles),
                roles,
                segregationOfDutiesViolations(roles)
        );
    }

    private String authorizationLevel(List<String> roles) {
        if (roles.contains("ROLE_SUPER_ADMIN")) {
            return "L4_ADMIN";
        }
        if (roles.stream().anyMatch(role -> role.endsWith("_MANAGER") || role.endsWith("_ADMIN"))) {
            return "L3_MANAGER";
        }
        if (roles.stream().anyMatch(role -> role.contains("ACCOUNTANT") || role.contains("CONTROLLER") || role.contains("INTEGRATION"))) {
            return "L2_OPERATOR";
        }
        return "L1_VIEWER";
    }

    private List<String> segregationOfDutiesViolations(List<String> roles) {
        List<String> violations = new ArrayList<>();
        addViolationIfPresent(roles, violations, "ROLE_AUDITOR", "ROLE_FI_ACCOUNTANT", "Audit and posting conflict");
        addViolationIfPresent(roles, violations, "ROLE_FI_ACCOUNTANT", "ROLE_MM_MANAGER", "Procure-to-pay conflict");
        addViolationIfPresent(roles, violations, "ROLE_SD_MANAGER", "ROLE_FI_ACCOUNTANT", "Order-to-cash conflict");
        return violations.stream().sorted(Comparator.naturalOrder()).toList();
    }

    private void addViolationIfPresent(List<String> roles, List<String> violations, String firstRole, String secondRole, String label) {
        if (roles.contains(firstRole) && roles.contains(secondRole)) {
            violations.add(label + " (" + firstRole + " + " + secondRole + ")");
        }
    }
}
