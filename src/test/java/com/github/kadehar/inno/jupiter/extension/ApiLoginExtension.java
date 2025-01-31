package com.github.kadehar.inno.jupiter.extension;

import com.github.kadehar.inno.api.model.UserJson;
import com.github.kadehar.inno.api.service.TokenApi;
import com.github.kadehar.inno.api.service.impl.TokenApiImpl;
import com.github.kadehar.inno.jupiter.annotation.ApiLogin;
import com.github.kadehar.inno.utils.Key;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;

public class ApiLoginExtension implements BeforeEachCallback {

    public static final ExtensionContext.Namespace NAMESPACE =
            ExtensionContext.Namespace.create(ApiLoginExtension.class);

    @Override
    public void beforeEach(ExtensionContext context) {
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), ApiLogin.class).ifPresent(_ -> {
            UserJson userJson = PreconditionsExtension.getUser();
            TokenApi tokenApi = new TokenApiImpl();
            String token = tokenApi.fetchToken(userJson);
            setToken(token);
        });
    }

    public static void setToken(String token) {
        TestMethodContextExtension.context().getStore(NAMESPACE).put(Key.TOKEN, token);
    }

    public static String getToken() {
        return TestMethodContextExtension.context().getStore(NAMESPACE).get(Key.TOKEN, String.class);
    }
}
