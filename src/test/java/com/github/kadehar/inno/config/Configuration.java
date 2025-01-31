package com.github.kadehar.inno.config;

import com.github.kadehar.inno.config.model.ApiConfig;
import com.github.kadehar.inno.config.model.DbConfig;
import com.github.kadehar.inno.config.model.UserConfig;
import org.aeonbits.owner.ConfigFactory;

public enum Configuration implements Config {
    INSTANCE;

    private static final ApiConfig API_CONFIG = ConfigFactory.create(
            ApiConfig.class,
            System.getProperties()
    );
    private static final DbConfig DB_CONFIG = ConfigFactory.create(
            DbConfig.class,
            System.getProperties()
    );
    private static final UserConfig USER_CONFIG = ConfigFactory.create(
            UserConfig.class,
            System.getProperties()
    );


    @Override
    public String dbUrl() {
        return DB_CONFIG.url();
    }

    @Override
    public String dbLogin() {
        return DB_CONFIG.login();
    }

    @Override
    public String dbPassword() {
        return DB_CONFIG.password();
    }

    @Override
    public String apiUrl() {
        return API_CONFIG.url();
    }

    @Override
    public String userName() {
        return USER_CONFIG.login();
    }

    @Override
    public String userPassword() {
        return USER_CONFIG.password();
    }

    @Override
    public String userRole() {
        return USER_CONFIG.role();
    }
}
