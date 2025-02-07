package ld.keycloak.spi.event.adapter;

import ld.domain.feature.registeruser.PersistUserPort;
import ld.domain.user.User;
import ld.keycloak.spi.event.db.ConnectionPool;
import ld.keycloak.spi.event.db.UserUtils;

import java.sql.*;
import java.util.logging.Logger;

public class RegisterUserAdapter implements PersistUserPort {

    private static final Logger LOGGER = Logger.getLogger(RegisterUserAdapter.class.getName());

    private static final String INSERT_USER =
            "INSERT INTO users (user_name, user_surname, user_email, user_birthdate, keycloak_id) " +
                    "VALUES (?, ?, ?, ?, ?) RETURNING *";

    @Override
    public User saveUser(User user) {
        LOGGER.info("Starting to save user with Keycloak ID: " + user.uuid());

        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_USER)) {

            LOGGER.info("Preparing SQL statement to insert user: " + user.name().value() + " " + user.surname().value());

            ps.setString(1, user.name().value());
            ps.setString(2, user.surname().value());
            ps.setString(3, user.email().value());
            ps.setDate(4, java.sql.Date.valueOf(user.birthDate().value()));
            ps.setObject(5, user.uuid());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    LOGGER.info("User successfully saved with ID db : " + rs.getInt("id"));
                    return UserUtils.mapToUser(rs);
                } else {
                    LOGGER.severe("User creation failed, no rows returned");
                    throw new RuntimeException("User creation failed");
                }
            }

        } catch (SQLException e) {
            LOGGER.severe("SQLException occurred while saving user: " + e.getMessage());
            throw new RuntimeException("Failed to save user", e);
        }
    }
}
