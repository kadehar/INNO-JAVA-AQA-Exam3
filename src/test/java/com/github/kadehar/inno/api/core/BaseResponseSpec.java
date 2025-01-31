package com.github.kadehar.inno.api.core;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.ResponseSpecification;

public class BaseResponseSpec {
    public static ResponseSpecification baseResponseSpecification() {
        return new ResponseSpecBuilder().log(LogDetail.ALL).build();
    }
}
