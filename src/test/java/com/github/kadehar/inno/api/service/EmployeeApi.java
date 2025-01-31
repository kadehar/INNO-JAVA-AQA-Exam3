package com.github.kadehar.inno.api.service;

import com.github.kadehar.inno.api.model.EmployeeJson;

import java.util.List;

public interface EmployeeApi {
    Long create(EmployeeJson employeeJson);
    List<EmployeeJson> findAll(Long companyId);
    EmployeeJson getById(Long id);
    EmployeeJson update(EmployeeJson employeeJson);
}
