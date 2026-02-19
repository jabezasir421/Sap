package com.example.sap.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public class JwtAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Set<String> authorities = new HashSet<>();
        authorities.addAll(readScopes(jwt));
        authorities.addAll(readRealmRoles(jwt));
        authorities.addAll(readResourceRoles(jwt));
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private Set<String> readScopes(Jwt jwt) {
        Set<String> scopes = new HashSet<>();
        List<String> scopeClaim = jwt.getClaimAsStringList("scope");
        if (scopeClaim != null) {
            scopes.addAll(scopeClaim.stream().map(scope -> "SCOPE_" + scope).toList());
        } else {
            String scopeString = jwt.getClaimAsString("scope");
            if (scopeString != null && !scopeString.isBlank()) {
                for (String value : scopeString.split(" ")) {
                    scopes.add("SCOPE_" + value);
                }
            }
        }
        return scopes;
    }

    @SuppressWarnings("unchecked")
    private Set<String> readRealmRoles(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess == null) {
            return Set.of();
        }
        Object rolesValue = realmAccess.get("roles");
        if (!(rolesValue instanceof List<?> roles)) {
            return Set.of();
        }
        return roles.stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .map(this::toRole)
                .collect(Collectors.toSet());
    }

    @SuppressWarnings("unchecked")
    private Set<String> readResourceRoles(Jwt jwt) {
        Object resourceAccess = jwt.getClaim("resource_access");
        if (!(resourceAccess instanceof Map<?, ?> resourceAccessMap)) {
            return Set.of();
        }

        Set<String> roles = new HashSet<>();
        for (Object value : resourceAccessMap.values()) {
            if (!(value instanceof Map<?, ?> clientAccess)) {
                continue;
            }
            Object roleValue = clientAccess.get("roles");
            if (!(roleValue instanceof List<?> roleList)) {
                continue;
            }
            for (Object role : roleList) {
                if (role instanceof String roleName) {
                    roles.add(toRole(roleName));
                }
            }
        }
        return roles;
    }

    private String toRole(String role) {
        return "ROLE_" + role.toUpperCase().replace('-', '_');
    }
}
