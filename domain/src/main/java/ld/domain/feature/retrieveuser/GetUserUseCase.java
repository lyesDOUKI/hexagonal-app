package ld.domain.feature.retrieveuser;

import ld.domain.user.User;

import java.util.Optional;

public interface GetUserUseCase {
    Optional<User> getUserById(GetUserByIdQuery userByIdQuery);
}
