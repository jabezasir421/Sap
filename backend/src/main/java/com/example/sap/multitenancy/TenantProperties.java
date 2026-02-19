package com.example.sap.multitenancy;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.multitenancy")
public record TenantProperties(boolean allowHeaderTenant) {
}
