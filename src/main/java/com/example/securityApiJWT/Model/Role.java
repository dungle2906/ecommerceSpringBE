package com.example.securityApiJWT.Model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.securityApiJWT.Model.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER(Collections.emptySet()),
    ADMIN(
            Set.of(
                    ADMIN_VIEW,
                    ADMIN_INSERT,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    MANAGER_VIEW,
                    MANAGER_INSERT,
                    MANAGER_UPDATE,
                    MANAGER_DELETE)
    ),
    MANAGER(
            Set.of(
            MANAGER_VIEW,
            MANAGER_INSERT,
            MANAGER_UPDATE,
            MANAGER_DELETE)
    );

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermissions()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    };
}
