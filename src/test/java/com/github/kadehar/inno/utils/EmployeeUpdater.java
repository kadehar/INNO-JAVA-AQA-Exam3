package com.github.kadehar.inno.utils;

import com.github.kadehar.inno.api.model.EmployeeJson;

public class EmployeeUpdater {
    public static EmployeeJson update(EmployeeJson employeeJson, Field field) {
        return switch (field) {
            case URL -> employeeJson.withUrl(RandomDataUtils.randomUrl());
            case EMAIL -> employeeJson.withEmail(RandomDataUtils.randomEmail());
            case PHONE -> employeeJson.withPhone(RandomDataUtils.randomPhone());
        };
    }

    public enum Field {
        EMAIL, PHONE, URL
    }
}
