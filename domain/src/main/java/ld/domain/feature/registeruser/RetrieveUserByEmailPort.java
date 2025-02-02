package ld.domain.feature.registeruser;

import ld.domain.user.User;
import ld.domain.user.information.Email;

import java.util.Optional;

public interface RetrieveUserByEmailPort {
    Optional<User> getUserByEmail(Email email);
}
