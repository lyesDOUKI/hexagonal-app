package ld.domain.feature.retrieveuser;

import ld.domain.user.User;

import java.util.Optional;
import java.util.UUID;

public interface RetrieveUserPort {
    Optional<User> getUserById(UUID userId);
}
