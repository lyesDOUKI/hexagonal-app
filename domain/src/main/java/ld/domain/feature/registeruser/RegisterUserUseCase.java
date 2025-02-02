package ld.domain.feature.registeruser;

import ld.domain.user.User;

public interface RegisterUserUseCase {
    User execute(CreateUserCommand createUserCommand);
}
