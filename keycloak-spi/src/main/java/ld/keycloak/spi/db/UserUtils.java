package ld.keycloak.spi.db;

import ld.domain.user.User;
import ld.domain.user.information.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserUtils {

    public static User mapToUser(ResultSet rs) throws SQLException {
        UserId userId = new UserId(rs.getLong("id"));
        UUID uuid = UUID.fromString(rs.getString("keycloak_id"));
        Name name = new Name(rs.getString("user_name"));
        Surname surname = new Surname(rs.getString("user_surname"));
        Email email = new Email(rs.getString("user_email"));
        BirthDate birthDate = new BirthDate(rs.getDate("user_birthDate").toLocalDate());
        return new User(uuid, userId, name, surname, email, birthDate);
    }
}
