package com.github.kadehar.inno.db;

import com.github.kadehar.inno.config.Config;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

public class Databases {

    private Databases() {
    }

    private static final Map<String, DataSource> SOURCES = new ConcurrentHashMap<>();
    private static final Map<Long, Map<String, Connection>> THREAD_CONNECTIONS = new ConcurrentHashMap<>();
    private static final Config CFG = Config.getInstance();

    public static <T> T transaction(Function<Connection, T> function, String jdbcUrl) {
        Connection connection = null;
        try {
            connection = connection(jdbcUrl);
            connection.setAutoCommit(false);
            T result = function.apply(connection);
            connection.commit();
            connection.setAutoCommit(true);
            return result;
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                    connection.setAutoCommit(true);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new RuntimeException(e);
        }
    }

    public static void transaction(Consumer<Connection> consumer, String jdbcUrl) {
        Connection connection = null;
        try {
            connection = connection(jdbcUrl);
            connection.setAutoCommit(false);
            consumer.accept(connection);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                    connection.setAutoCommit(true);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new RuntimeException(e);
        }
    }

    private static DataSource dataSource(String jdbcUrl) {
        return SOURCES.computeIfAbsent(
                jdbcUrl,
                key -> {
                    PGSimpleDataSource ds = new PGSimpleDataSource();
                    ds.setUser(CFG.dbLogin());
                    ds.setPassword(CFG.dbPassword());
                    ds.setUrl(key);
                    return ds;
                }
        );
    }

    private static Connection connection(String jdbcUrl) {
        return THREAD_CONNECTIONS.computeIfAbsent(
                Thread.currentThread().threadId(),
                _ -> {
                    try {
                        return new HashMap<>(Map.of(
                                jdbcUrl,
                                dataSource(jdbcUrl).getConnection()
                        ));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        ).computeIfAbsent(
                jdbcUrl,
                _ -> {
                    try {
                        return dataSource(jdbcUrl).getConnection();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    public static void closeAllConnections() {
        for (Map<String, Connection> connectionMap : THREAD_CONNECTIONS.values()) {
            for (Connection connection : connectionMap.values()) {
                try {
                    if (connection != null && !connection.isClosed()) {
                        connection.close();
                    }
                } catch (SQLException ignore) {

                }
            }
        }
    }
}
