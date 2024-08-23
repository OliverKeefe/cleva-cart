package org.clevacart.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.clevacart.config.DatabaseConfig;
import org.intellij.lang.annotations.Language;

import java.sql.*;

import org.intellij.lang.annotations.Language;

@ApplicationScoped
public class DatabaseService {
    private Connection connection;

    public Connection getConnection(DatabaseConfig.DatabaseConnectionProperties properties) throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(properties.getJdbcUrl(), properties.getUsername(), properties.getPassword());
        }
        return connection;
    }

    public ResultSet executeQuery(@Language("SQL") String sql, DatabaseConfig.DatabaseConnectionProperties properties, Object... parameters) throws SQLException {
        Connection connection = getConnection(properties);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        for (int i = 0; i < parameters.length; i++) {
            preparedStatement.setObject(i + 1, parameters[i]);
        }

        return preparedStatement.executeQuery();
    }

    public int executeUpdate(String sql, DatabaseConfig.DatabaseConnectionProperties properties) throws SQLException {
        Connection connection = getConnection(properties);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        return preparedStatement.executeUpdate();
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
