package ld.domain.feature.updateuser;

import ld.domain.user.User;

public interface UpdateUserUseCase {
    User updateUser(UpdateUserCommand updateUserCommand);
}
