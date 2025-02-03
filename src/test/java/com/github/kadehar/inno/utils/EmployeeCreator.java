package com.github.kadehar.inno.utils;

import com.github.kadehar.inno.api.model.EmployeeJson;
import com.github.kadehar.inno.jupiter.extension.PreconditionsExtension;

import java.time.LocalDate;

public class EmployeeCreator {
    public static EmployeeJson newEmployee() {
        return new EmployeeJson(
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
    }
}
