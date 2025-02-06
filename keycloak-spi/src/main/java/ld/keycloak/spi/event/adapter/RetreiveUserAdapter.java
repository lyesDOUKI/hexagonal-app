package ld.keycloak.spi.event.adapter;

import ld.domain.feature.registeruser.RetrieveUserByEmailPort;
import ld.domain.user.User;
import ld.domain.user.information.Email;
import ld.keycloak.spi.event.db.ConnectionPool;
import ld.keycloak.spi.event.db.UserUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Logger;

public class RetreiveUserAdapter implements RetrieveUserByEmailPort {

    // Static logger declaration
    private static final Logger LOGGER = Logger.getLogger(RetreiveUserAdapter.class.getName());

    private static final String SELECT_USER_BY_EMAIL = """
            SELECT id, user_name, user_surname, user_email, user_birthdate 
            FROM users 
            WHERE user_email = ?
            """;

    @Override
    public Optional<User> getUserByEmail(Email email) {
        LOGGER.info("Starting to retrieve user by email: " + email.value());

        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_USER_BY_EMAIL)) {

            ps.setString(1, email.value());
            LOGGER.info("Executing query to retrieve user with email: " + email.value());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    LOGGER.info("User found for email: " + email.value());
                    return Optional.of(UserUtils.mapToUser(rs));
                }
                LOGGER.warning("No user found for email: " + email.value());
                return Optional.empty();
            }

        } catch (SQLException e) {
            LOGGER.severe("SQLException occurred while retrieving user by email: " + email.value() + " - " + e.getMessage());
            throw new RuntimeException("Failed to get user by email", e);
        }
    }
}
