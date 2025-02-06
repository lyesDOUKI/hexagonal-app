package ld.keycloak.common;

import ld.keycloak.spi.event.db.DatabaseConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MessageUtils {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = DatabaseConfig.class.getClassLoader()
                .getResourceAsStream("messages.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Cannot load configuration", e);
        }
    }

    public static String getMessage(String key){
        try {
            return props.getProperty(key) != null ? props.getProperty(key) : "Aucun message trouvé avec la clè : " + key;
        }catch (Exception e){
            throw new RuntimeException("enable to get message : " + e.getMessage());
        }
    }
}
