package com.github.kadehar.inno.api.service.impl;

import com.github.kadehar.inno.api.core.endpoints.EndpointPath;
import com.github.kadehar.inno.api.model.EmployeeJson;
import com.github.kadehar.inno.api.service.EmployeeApi;

import java.util.List;

import static com.github.kadehar.inno.api.core.BaseRequestSpecification.authSpecification;
import static com.github.kadehar.inno.api.core.BaseRequestSpecification.noAuthSpecification;
import static com.github.kadehar.inno.api.core.BaseResponseSpec.baseResponseSpecification;
import static io.restassured.RestAssured.given;

public class EmployeeApiImpl implements EmployeeApi {

    @Override
    public Long create(EmployeeJson employeeJson) {
        // @formatter:off
        return given()
                    .spec(authSpecification())
                    .body(employeeJson)
                .when()
                    .post(EndpointPath.employee())
                .then()
                    .spec(baseResponseSpecification())
                    .extract()
                    .jsonPath()
                    .getLong("id");
        // @formatter:on
    }

    @Override
    public List<EmployeeJson> findAll(Long companyId) {
        // @formatter:off
        return given()
                    .spec(noAuthSpecification())
                    .queryParam("company", companyId)
                .when()
                    .get(EndpointPath.employee())
                .then()
                    .spec(baseResponseSpecification())
                    .extract()
                    .jsonPath().getList("", EmployeeJson.class);
        // @formatter:on
    }

    @Override
    public EmployeeJson getById(Long id) {
        // @formatter:off
        return given()
                    .spec(noAuthSpecification())
                .when()
                    .get(EndpointPath.employee() + "/{id}", id)
                .then()
                    .spec(baseResponseSpecification())
                    .extract()
                    .as(EmployeeJson.class);
        // @formatter:on
    }

    @Override
    public EmployeeJson update(EmployeeJson employeeJson) {
        // @formatter:off
        return given()
                    .spec(authSpecification())
                    .body(employeeJson)
                .when()
                    .patch(EndpointPath.employee() + "/{id}", employeeJson.id())
                .then()
                    .spec(baseResponseSpecification())
                    .extract()
                    .as(EmployeeJson.class);
        // @formatter:on
    }
}
