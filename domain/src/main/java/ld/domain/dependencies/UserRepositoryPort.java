package ld.domain.dependencies;

import ld.domain.user.User;
import ld.domain.user.information.Email;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPort {
    User saveUser(User user);
    Optional<User> getUserById(UUID userId);
    Optional<User> getUserByEmail(Email email);
    User updateUser(User user);
}
