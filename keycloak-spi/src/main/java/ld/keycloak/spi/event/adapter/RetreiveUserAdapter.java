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

public class RetreiveUserAdapter implements RetrieveUserByEmailPort {
    private static final String SELECT_USER_BY_EMAIL = """
            SELECT id, user_name, user_surname, user_email, user_birthdate 
            FROM users 
            WHERE user_email = ?
            """;
    @Override
    public Optional<User> getUserByEmail(Email email) {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_USER_BY_EMAIL)) {

            ps.setString(1, email.value());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(UserUtils.mapToUser(rs));
                }
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to get user by email", e);
        }
    }
}
