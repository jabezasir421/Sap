package com.example.sap.multitenancy;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class TenantFilter extends OncePerRequestFilter {

    private static final String TENANT_HEADER = "X-Tenant-Id";

    private final TenantProperties tenantProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            Optional<String> tenant = extractTenant(request);
            if (tenant.isEmpty()) {
                response.sendError(HttpStatus.BAD_REQUEST.value(), "Missing tenant. Provide claim 'tenant_id' or X-Tenant-Id in dev.");
                return;
            }

            TenantContext.setTenant(tenant.get());
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return HttpMethod.OPTIONS.matches(request.getMethod())
                || uri.startsWith("/actuator/health")
                || uri.startsWith("/swagger-ui")
                || uri.startsWith("/v3/api-docs")
                || uri.startsWith("/error");
    }

    private Optional<String> extractTenant(HttpServletRequest request) {
        Authentication authentication = (Authentication) request.getUserPrincipal();
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            Jwt jwt = jwtAuth.getToken();
            String tenantFromToken = jwt.getClaimAsString("tenant_id");
            if (tenantFromToken != null && !tenantFromToken.isBlank()) {
                return Optional.of(tenantFromToken);
            }
        }

        if (tenantProperties.allowHeaderTenant()) {
            String tenantFromHeader = request.getHeader(TENANT_HEADER);
            if (tenantFromHeader != null && !tenantFromHeader.isBlank()) {
                return Optional.of(tenantFromHeader);
            }
        }

        return Optional.empty();
    }
}
