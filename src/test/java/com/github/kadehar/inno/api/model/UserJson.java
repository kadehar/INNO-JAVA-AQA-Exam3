package com.github.kadehar.inno.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.kadehar.inno.db.entity.AppUserEntity;

public record UserJson(
        @JsonProperty("username")
        String username,
        @JsonProperty("password")
        String password
) {
    public static UserJson fromEntity(AppUserEntity entity) {
        return new UserJson(
                entity.getLogin(),
                entity.getPassword()
        );
    }
}
