package com.github.kadehar.inno.utils;

import com.github.kadehar.inno.api.model.EmployeeJson;
import com.github.kadehar.inno.jupiter.extension.PreconditionsExtension;

public class EmployeeUpdater {
    public static EmployeeJson updateEmployeePhone(EmployeeJson employeeJson) {
        return new EmployeeJson(
                employeeJson.id(),
                employeeJson.firstName(),
                employeeJson.lastName(),
                employeeJson.middleName(),
                PreconditionsExtension.getCompanyId(),
                employeeJson.email(),
                employeeJson.url(),
                RandomDataUtils.randomPhone(),
                employeeJson.birthDate(),
                employeeJson.active()
        );
    }
}
