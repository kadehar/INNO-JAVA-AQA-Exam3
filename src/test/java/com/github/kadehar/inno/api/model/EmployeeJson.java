package com.github.kadehar.inno.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record EmployeeJson(
        @JsonProperty("id")
        Long id,
        @JsonProperty("firstName")
        String firstName,
        @JsonProperty("lastName")
        String lastName,
        @JsonProperty("middleName")
        String middleName,
        @JsonProperty("companyId")
        Long companyId,
        @JsonProperty("email")
        String email,
        @JsonProperty("url")
        String url,
        @JsonProperty("phone")
        String phone,
        @JsonProperty("birthdate")
        String birthDate,
        @JsonProperty("isActive")
        Boolean active
) {
    public EmployeeJson withId(Long newId) {
        return new EmployeeJson(
                newId,
                firstName(),
                lastName(),
                middleName(),
                companyId(),
                email(),
                url(),
                phone(),
                birthDate(),
                active()
        );
    }

    public EmployeeJson withUrl(String newUrl) {
        return new EmployeeJson(
                id(),
                firstName(),
                lastName(),
                middleName(),
                companyId(),
                email(),
                newUrl,
                phone(),
                birthDate(),
                active()
        );
    }

    public EmployeeJson withEmail(String newEmail) {
        return new EmployeeJson(
                id(),
                firstName(),
                lastName(),
                middleName(),
                companyId(),
                newEmail,
                url(),
                phone(),
                birthDate(),
                active()
        );
    }

    public EmployeeJson withPhone(String newPhone) {
        return new EmployeeJson(
                id(),
                firstName(),
                lastName(),
                middleName(),
                companyId(),
                email(),
                url(),
                newPhone,
                birthDate(),
                active()
        );
    }
}
