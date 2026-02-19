package com.example.sap.audit;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuditLogInterceptor implements HandlerInterceptor {

    private static final String START_NANOS = "audit-start-nanos";

    private final AuditLogService auditLogService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute(START_NANOS, System.nanoTime());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String path = request.getRequestURI();
        if (path.startsWith("/actuator") || path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")) {
            return;
        }

        Object startNanos = request.getAttribute(START_NANOS);
        long durationMs = 0L;
        if (startNanos instanceof Long nanos) {
            durationMs = (System.nanoTime() - nanos) / 1_000_000;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication != null && authentication.isAuthenticated() ? authentication.getName() : "anonymous";
        String clientIp = request.getRemoteAddr() == null ? "unknown" : request.getRemoteAddr();
        auditLogService.record(username, request.getMethod(), path, response.getStatus(), durationMs, clientIp);
    }
}
