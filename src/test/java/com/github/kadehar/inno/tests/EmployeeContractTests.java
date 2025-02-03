package com.github.kadehar.inno.tests;

import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.kadehar.inno.api.core.endpoints.EndpointPath;
import com.github.kadehar.inno.api.model.EmployeeJson;
import com.github.kadehar.inno.api.service.impl.EmployeeApiImpl;
import com.github.kadehar.inno.jupiter.annotation.ApiLogin;
import com.github.kadehar.inno.jupiter.annotation.TearDownEmployee;
import com.github.kadehar.inno.jupiter.annotation.WithEmployee;
import com.github.kadehar.inno.jupiter.annotation.WithPreconditions;
import com.github.kadehar.inno.jupiter.extension.PreconditionsExtension;
import com.github.kadehar.inno.jupiter.extension.TearDownEmployeeExtension;
import com.github.kadehar.inno.utils.EmployeeCreator;
import com.github.kadehar.inno.utils.EmployeeUpdater;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.fge.jsonschema.main.JsonSchemaFactory.newBuilder;
import static com.github.kadehar.inno.api.core.BaseRequestSpecification.authSpecification;
import static com.github.kadehar.inno.api.core.BaseRequestSpecification.noAuthSpecification;
import static com.github.kadehar.inno.api.core.BaseResponseSpec.baseResponseSpecification;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidatorSettings.settings;
import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeContractTests {

    @BeforeAll
    static void setUpValidator() {
        JsonSchemaValidator.settings = settings().with()
                .checkedValidation(true).and().with()
                .jsonSchemaFactory(newBuilder().setValidationConfiguration(
                        ValidationConfiguration.newBuilder()
                                .setDefaultVersion(SchemaVersion.DRAFTV4).freeze()
                ).freeze());
    }

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
        EmployeeJson employeeJson = EmployeeCreator.newEmployee();
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

    @Test
    @WithPreconditions
    @ApiLogin
    @TearDownEmployee
    @DisplayName("Code 200 when request employees")
    void code200WhenRequestEmployees() {
        EmployeeJson employeeJson = EmployeeCreator.newEmployee();
        TearDownEmployeeExtension.setEmployeeId(new EmployeeApiImpl().create(employeeJson));
        // @formatter:off
        given()
            .spec(noAuthSpecification())
            .queryParam("company", PreconditionsExtension.getCompanyId())
        .when()
            .get(EndpointPath.employee())
        .then()
            .spec(baseResponseSpecification())
            .statusCode(200);
        // @formatter:on
    }

    @Test
    @WithPreconditions
    @ApiLogin
    @WithEmployee
    @DisplayName("Code 201 when update employee's phone")
    void code201WhenEmployeeIsUpdated(EmployeeJson employeeJson) {
        EmployeeJson employeeWithNewPhone = EmployeeUpdater.updateEmployeePhone(employeeJson);
        // @formatter:off
        given()
            .spec(authSpecification())
            .body(employeeWithNewPhone)
        .when()
            .patch(EndpointPath.employee() + "/{id}", employeeWithNewPhone.id())
        .then()
            .spec(baseResponseSpecification())
            .statusCode(201);
        // @formatter:on
    }

    @Test
    @WithPreconditions
    @ApiLogin
    @WithEmployee
    @DisplayName("Code 404 if employee is not found while update")
    void code404IfEmployeeIsNotFoundWhileUpdate(EmployeeJson employeeJson) {
        EmployeeJson employeeWithNewPhone = EmployeeUpdater.updateEmployeePhone(employeeJson);
        // @formatter:off
        given()
            .spec(authSpecification())
            .body(employeeWithNewPhone)
        .when()
            .patch(EndpointPath.employee() + "/{id}", 0)
        .then()
            .spec(baseResponseSpecification())
            .statusCode(404);
        // @formatter:on
    }

    @Test
    @WithPreconditions
    @ApiLogin
    @TearDownEmployee
    @DisplayName("Create employee response body is correct")
    void checkCreateEmployeeBodyWithValidator() {
        EmployeeJson employeeJson = EmployeeCreator.newEmployee();
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
        response.then().body(matchesJsonSchemaInClasspath("create-employee-schema.json"));
    }

    @Test
    @WithPreconditions
    @ApiLogin
    @WithEmployee
    @DisplayName("Get employee response body is correct")
    void checkGetEmployeeBodyWithValidator(EmployeeJson employee) {
        // @formatter:off
        given()
            .spec(noAuthSpecification())
        .when()
            .get(EndpointPath.employee() + "/{id}", employee.id())
        .then()
            .spec(baseResponseSpecification())
            .body(matchesJsonSchemaInClasspath("employee-schema.json"));
        // @formatter:on
    }

    @Test
    @WithPreconditions
    @ApiLogin
    @TearDownEmployee
    @DisplayName("Get employees response body is correct")
    void checkGetEmployeesBodyWithValidator() {
        EmployeeJson employeeJson = EmployeeCreator.newEmployee();
        TearDownEmployeeExtension.setEmployeeId(new EmployeeApiImpl().create(employeeJson));
        // @formatter:off
        given()
            .spec(noAuthSpecification())
            .queryParam("company", PreconditionsExtension.getCompanyId())
        .when()
            .get(EndpointPath.employee())
        .then()
            .spec(baseResponseSpecification())
            .body(matchesJsonSchemaInClasspath("get-employees-schema.json"));
        // @formatter:on
    }

    @Test
    @WithPreconditions
    @ApiLogin
    @WithEmployee
    @DisplayName("Update employee response body is correct")
    void checkUpdateEmployeeBodyWithValidator(EmployeeJson employeeJson) {
        EmployeeJson employeeWithNewPhone = EmployeeUpdater.updateEmployeePhone(employeeJson);
        // @formatter:off
        given()
            .spec(authSpecification())
            .body(employeeWithNewPhone)
        .when()
            .patch(EndpointPath.employee() + "/{id}", employeeWithNewPhone.id())
        .then()
            .spec(baseResponseSpecification())
            .body(matchesJsonSchemaInClasspath("employee-schema.json"));
        // @formatter:on
    }
}
