package com.example.security.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {

    ROLE_USER("user"),
    ROLE_ADMIN("admin");

    private final String value;
}
