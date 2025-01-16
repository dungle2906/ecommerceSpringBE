package com.example.securityApiJWT.Model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    ADMIN_VIEW("admin:viewData"),
    ADMIN_INSERT("admin:insertData"),
    ADMIN_UPDATE("admin:updateData"),
    ADMIN_DELETE("admin:deleteData"),
    MANAGER_VIEW("manager:viewData"),
    MANAGER_INSERT("manager:insertData"),
    MANAGER_UPDATE("manager:updateData"),
    MANAGER_DELETE("manager:deleteData");

    private final String permissions;
}
