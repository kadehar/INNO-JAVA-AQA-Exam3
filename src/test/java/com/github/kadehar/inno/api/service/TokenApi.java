package com.github.kadehar.inno.api.service;

import com.github.kadehar.inno.api.model.UserJson;

public interface TokenApi {
    String fetchToken(UserJson userJson);
}
