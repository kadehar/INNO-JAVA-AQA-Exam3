package com.github.kadehar.inno.jupiter.extension;

import com.github.kadehar.inno.db.service.EmployeeDbClient;
import com.github.kadehar.inno.utils.Key;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TearDownEmployeeExtension implements AfterEachCallback {

    public static final ExtensionContext.Namespace NAMESPACE =
            ExtensionContext.Namespace.create(TearDownEmployeeExtension.class);
    private final EmployeeDbClient dbClient = new EmployeeDbClient();

    @Override
    public void afterEach(ExtensionContext context) {
        dbClient.deleteEmployeeById(getEmployeeId());
    }

    public static void setEmployeeId(Long employeeId) {
        TestMethodContextExtension.context().getStore(NAMESPACE).put(Key.EMPLOYEE_ID, employeeId);
    }

    public static Long getEmployeeId() {
        return TestMethodContextExtension.context().getStore(NAMESPACE).get(Key.EMPLOYEE_ID, Long.class);
    }
}
