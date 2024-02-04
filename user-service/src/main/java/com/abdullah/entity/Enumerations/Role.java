package com.abdullah.entity.Enumerations;


import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    USER("USER"),
    OWNER("OWNER"),
    ADMIN("ADMIN"),;

    private final String value;

    Role(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
