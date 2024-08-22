package org.clevacart.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.clevacart.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@ApplicationScoped
public class DatabaseService {
    private Connection connection;

    public Connection getConnection(DatabaseConfig.DatabaseConnectionProperties properties) throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(properties.getJdbcUrl(), properties.getUsername(), properties.getPassword());
        }
        return connection;
    }
}
