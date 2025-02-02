package ld.keycloak.spi.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = DatabaseConfig.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Cannot load configuration", e);
        }
    }

    public static String getUrl() {
        return props.getProperty("db.url");
    }

    public static String getUsername() {
        return props.getProperty("db.username");
    }

    public static String getPassword() {
        return props.getProperty("db.password");
    }
}
