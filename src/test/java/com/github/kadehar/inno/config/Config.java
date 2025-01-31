package com.github.kadehar.inno.config;

public interface Config {

    static Config getInstance() {
        return Configuration.INSTANCE;
    }

    String dbUrl();
    String dbLogin();
    String dbPassword();
    String apiUrl();
    String userName();
    String userPassword();
    String userRole();
}
