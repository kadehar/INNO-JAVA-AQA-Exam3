package com.github.kadehar.inno.api.core;

import com.github.kadehar.inno.config.Config;
import com.github.kadehar.inno.jupiter.extension.ApiLoginExtension;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseRequestSpecification {

    private static final Config CFG = Config.getInstance();
    private static final String X_CLIENT_TOKEN_HEADER = "x-client-token";

    public static RequestSpecification authSpecification() {
        return defaultBuilder()
                .addHeader(X_CLIENT_TOKEN_HEADER, ApiLoginExtension.getToken())
                .build();
    }

    public static RequestSpecification noAuthSpecification() {
        return defaultBuilder().build();
    }

    private static RequestSpecBuilder defaultBuilder() {
        return new RequestSpecBuilder()
                .setBaseUri(CFG.apiUrl())
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL);
    }
}
