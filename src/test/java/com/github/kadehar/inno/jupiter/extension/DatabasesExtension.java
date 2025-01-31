package com.github.kadehar.inno.jupiter.extension;

import com.github.kadehar.inno.db.Databases;

public class DatabasesExtension implements SuiteExtension {

    @Override
    public void afterSuite() {
        Databases.closeAllConnections();
    }
}

