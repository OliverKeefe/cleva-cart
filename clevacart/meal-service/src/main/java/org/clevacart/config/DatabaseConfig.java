package org.clevacart.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class DatabaseConfig {

    @Inject
    @ConfigProperty(name = "quarkus.datasource.jdbc.url")
    String jdbcUrl;

    @Inject
    @ConfigProperty(name = "quarkus.datasource.username")
    String username;

    @Inject
    @ConfigProperty(name = "quarkus.datasource.password")
    String password;

    public DatabaseConfig() {

    }

    @ApplicationScoped
    public DatabaseConnectionProperties getDatabaseConnectionProperties() {
        return new DatabaseConnectionProperties(jdbcUrl, username, password);
    }

    public static class DatabaseConnectionProperties {
        private final String jdbcUrl;
        private final String username;
        private final String password;

        public DatabaseConnectionProperties(String jdbcUrl, String username, String password) {
            this.jdbcUrl = jdbcUrl;
            this.username = username;
            this.password = password;
        }

        public String getJdbcUrl() {
            return jdbcUrl;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}

