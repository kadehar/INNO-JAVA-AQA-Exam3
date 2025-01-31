package com.github.kadehar.inno.config.model;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:config.properties",
        "file:~/config.properties",
        "file:./config.properties"
})
public interface UserConfig extends Config {
    @Key("userName")
    String login();
    @Key("userPassword")
    String password();
    @Key("userRole")
    String role();
}
