package ld.keycloak.spi.event.adapter;

import ld.domain.feature.registeruser.PersistUserPort;
import ld.domain.user.User;
import ld.keycloak.spi.event.db.ConnectionPool;
import ld.keycloak.spi.event.db.UserUtils;

import java.sql.*;

public class RegisterUserAdapter implements PersistUserPort {
    private static final String INSERT_USER =
            "INSERT INTO users (user_name, user_surname, user_email, user_birthdate, keycloak_id) " +
                    "VALUES (?, ?, ?, ?, ?) RETURNING *";
    @Override
    public User saveUser(User user) {
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_USER)) {

            ps.setString(1, user.name().value());
            ps.setString(2, user.surname().value());
            ps.setString(3, user.email().value());
            ps.setDate(4, java.sql.Date.valueOf(user.birthDate().value()));
            ps.setObject(5, user.uuid());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return UserUtils.mapToUser(rs);
                }
                throw new RuntimeException("User creation failed");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to save user", e);
        }
    }

}
