package com.github.kadehar.inno.tests;

import com.github.kadehar.inno.api.model.EmployeeJson;
import com.github.kadehar.inno.api.service.EmployeeApi;
import com.github.kadehar.inno.api.service.impl.EmployeeApiImpl;
import com.github.kadehar.inno.jupiter.annotation.ApiLogin;
import com.github.kadehar.inno.jupiter.annotation.WithEmployee;
import com.github.kadehar.inno.jupiter.annotation.WithPreconditions;
import com.github.kadehar.inno.jupiter.extension.PreconditionsExtension;
import com.github.kadehar.inno.utils.RandomDataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@WithPreconditions
public class EmployeeBusinessTests {

    private final EmployeeApi employeeApi = new EmployeeApiImpl();

    @Test
    @ApiLogin
    @WithEmployee
    @DisplayName("Can create new employee")
    void canCreateNewEmployee(EmployeeJson employee) {
        assertThat(employee.id()).isNotNull();
        assertThat(employee.id()).isGreaterThan(0);
    }

    @Test
    @ApiLogin
    @WithEmployee
    @DisplayName("Can get employees by their company id")
    void canGetEmployeesByCompanyId(EmployeeJson employee) {
        List<EmployeeJson> employees = employeeApi.findAll(PreconditionsExtension.getCompanyId());
        assertThat(employees).isNotEmpty();
        assertThat(employees).contains(employee);
    }

    @Test
    @ApiLogin
    @WithEmployee
    @DisplayName("Can get employee by its id")
    void canGetEmployeeById(EmployeeJson employee) {
        EmployeeJson existingEmployee = employeeApi.getById(employee.id());
        assertThat(employee).isEqualTo(existingEmployee);
    }

    @Test
    @ApiLogin
    @WithEmployee
    @DisplayName("Can update employee information")
    void canUpdateEmployeeData(EmployeeJson employee) {
        EmployeeJson employeeWithNewPhone = new EmployeeJson(
                employee.id(),
                employee.firstName(),
                employee.lastName(),
                employee.middleName(),
                PreconditionsExtension.getCompanyId(),
                employee.email(),
                employee.url(),
                RandomDataUtils.randomPhone(),
                employee.birthDate(),
                employee.active()
        );
        EmployeeJson updatedEmployee = employeeApi.update(employeeWithNewPhone);
        assertThat(employeeWithNewPhone).isEqualTo(updatedEmployee);
    }
}
