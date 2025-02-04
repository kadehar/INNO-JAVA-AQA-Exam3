package com.github.kadehar.inno.jupiter.extension;

import com.github.kadehar.inno.api.model.EmployeeJson;
import com.github.kadehar.inno.api.service.EmployeeApi;
import com.github.kadehar.inno.api.service.impl.EmployeeApiImpl;
import com.github.kadehar.inno.db.service.EmployeeDbClient;
import com.github.kadehar.inno.utils.EmployeeCreator;
import com.github.kadehar.inno.utils.Key;
import org.junit.jupiter.api.extension.*;

public class EmployeeExtension implements ParameterResolver, BeforeEachCallback, AfterEachCallback {

    public static final ExtensionContext.Namespace NAMESPACE =
            ExtensionContext.Namespace.create(EmployeeExtension.class);
    private final EmployeeDbClient dbClient = new EmployeeDbClient();
    private final EmployeeApi employeeApi = new EmployeeApiImpl();

    @Override
    public void beforeEach(ExtensionContext context) {
        EmployeeJson employeeJson = EmployeeCreator.newEmployee();
        Long employeeId = employeeApi.create(employeeJson);
        setEmployeeId(employeeId);
        setEmployee(employeeJson.withId(employeeId));
    }

    @Override
    public void afterEach(ExtensionContext context) {
        dbClient.deleteEmployeeById(getEmployeeId());
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == EmployeeJson.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return getEmployee();
    }

    public static void setEmployee(EmployeeJson employee) {
        TestMethodContextExtension.context().getStore(NAMESPACE).put(Key.EMPLOYEE, employee);
    }

    public static EmployeeJson getEmployee() {
        return TestMethodContextExtension.context().getStore(NAMESPACE).get(Key.EMPLOYEE, EmployeeJson.class);
    }

    public static void setEmployeeId(Long employeeId) {
        TestMethodContextExtension.context().getStore(NAMESPACE).put(Key.EMPLOYEE_ID, employeeId);
    }

    public static Long getEmployeeId() {
        return TestMethodContextExtension.context().getStore(NAMESPACE).get(Key.EMPLOYEE_ID, Long.class);
    }
}
