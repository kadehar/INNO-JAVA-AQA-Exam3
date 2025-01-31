package com.github.kadehar.inno.config.model;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:config.properties",
        "file:~/config.properties",
        "file:./config.properties"
})
public interface DbConfig extends Config {
    @Key("dbUrl")
    String url();
    @Key("dbUser")
    String login();
    @Key("dbPassword")
    String password();
}
