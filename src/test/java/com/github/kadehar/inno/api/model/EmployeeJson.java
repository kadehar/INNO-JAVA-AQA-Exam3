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
}
