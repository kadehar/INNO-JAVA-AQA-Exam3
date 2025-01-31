package com.github.kadehar.inno.tests;

import com.github.kadehar.inno.api.core.endpoints.EndpointPath;
import com.github.kadehar.inno.api.model.EmployeeJson;
import com.github.kadehar.inno.jupiter.annotation.ApiLogin;
import com.github.kadehar.inno.jupiter.annotation.TearDownEmployee;
import com.github.kadehar.inno.jupiter.annotation.WithEmployee;
import com.github.kadehar.inno.jupiter.annotation.WithPreconditions;
import com.github.kadehar.inno.jupiter.extension.PreconditionsExtension;
import com.github.kadehar.inno.jupiter.extension.TearDownEmployeeExtension;
import com.github.kadehar.inno.utils.RandomDataUtils;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.github.kadehar.inno.api.core.BaseRequestSpecification.authSpecification;
import static com.github.kadehar.inno.api.core.BaseRequestSpecification.noAuthSpecification;
import static com.github.kadehar.inno.api.core.BaseResponseSpec.baseResponseSpecification;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeContractTests {

    @Test
    @DisplayName("Code 404 if employee is not found")
    void code404IfEmployeeIsNotFound() {
        // @formatter:off
        given()
            .spec(noAuthSpecification())
        .when()
            .get(EndpointPath.employee() + "/0")
        .then()
            .spec(baseResponseSpecification())
            .statusCode(404);
        // @formatter:on
    }

    @Test
    @WithPreconditions
    @ApiLogin
    @WithEmployee
    @DisplayName("Code 200 if employee is found")
    void code200IfEmployeeIsFound(EmployeeJson employee) {
        // @formatter:off
        given()
            .spec(noAuthSpecification())
        .when()
            .get(EndpointPath.employee() + "/{id}", employee.id())
        .then()
            .spec(baseResponseSpecification())
            .statusCode(200);
        // @formatter:on
    }

    @Test
    @WithPreconditions
    @ApiLogin
    @TearDownEmployee
    @DisplayName("Code 201 if employee is created")
    void code201IfEmployeeIsCreated() {
        EmployeeJson employeeJson = new EmployeeJson(
                null,
                RandomDataUtils.randomFirstName(),
                RandomDataUtils.randomLastName(),
                RandomDataUtils.randomMiddleName(),
                PreconditionsExtension.getCompanyId(),
                RandomDataUtils.randomEmail(),
                RandomDataUtils.randomUrl(),
                RandomDataUtils.randomPhone(),
                LocalDate.now().minusYears(30).toString(),
                true
        );
        // @formatter:off
        Response response = given()
            .spec(authSpecification())
            .body(employeeJson)
        .when()
            .post(EndpointPath.employee())
        .then()
            .spec(baseResponseSpecification())
            .extract().response();
        // @formatter:on
        TearDownEmployeeExtension.setEmployeeId(response.jsonPath().getLong("id"));
        assertThat(response.statusCode()).isEqualTo(201);
    }
}
