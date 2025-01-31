package com.github.kadehar.inno.api.service.impl;

import com.github.kadehar.inno.api.model.UserJson;
import com.github.kadehar.inno.api.service.TokenApi;

import static com.github.kadehar.inno.api.core.BaseRequestSpecification.noAuthSpecification;
import static com.github.kadehar.inno.api.core.BaseResponseSpec.baseResponseSpecification;
import static com.github.kadehar.inno.api.core.endpoints.EndpointPath.login;
import static io.restassured.RestAssured.given;

public class TokenApiImpl implements TokenApi {

    @Override
    public String fetchToken(UserJson userJson) {
        // @formatter:off
        return given()
                    .spec(noAuthSpecification())
                    .body(userJson)
                .when()
                    .post(login())
                .then()
                    .spec(baseResponseSpecification())
                    .extract()
                    .jsonPath()
                    .getString("userToken");
        // @formatter:on
    }
}
