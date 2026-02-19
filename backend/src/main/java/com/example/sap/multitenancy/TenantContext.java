package com.example.sap.multitenancy;

import java.util.Optional;

public final class TenantContext {

    private static final ThreadLocal<String> CURRENT = new ThreadLocal<>();

    private TenantContext() {
    }

    public static void setTenant(String tenantId) {
        CURRENT.set(tenantId);
    }

    public static Optional<String> getTenant() {
        return Optional.ofNullable(CURRENT.get());
    }

    public static String requireTenant() {
        return getTenant().orElseThrow(() -> new IllegalStateException("Tenant context is missing for this request."));
    }

    public static void clear() {
        CURRENT.remove();
    }
}
