package ld.domain.feature.updateuser;

import ld.domain.user.exception.UserDomainException;
import ld.domain.user.User;
import ld.domain.dependencies.UserRepositoryPort;
import ld.domain.user.information.BirthDate;
import ld.domain.user.information.Email;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UpdateUserService implements UpdateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final UpdateUserValidation updateUserValidation;

    public UpdateUserService(UserRepositoryPort userRepositoryPort){
        this.userRepositoryPort = userRepositoryPort;
        this.updateUserValidation = new UpdateUserValidation();
    }
    @Override
    public User updateUser(UpdateUserCommand updateUserCommand) {
        List<RuntimeException> exceptions = new ArrayList<>();
        Optional<User> optionalUser = this.userRepositoryPort.getUserById(updateUserCommand.userId());
        User user = optionalUser.orElseThrow(()->this.throwDomainException(exceptions));
        this.updateUserValidation.validate(updateUserCommand, user, exceptions);

        Email newEmail = updateUserCommand.email().orElse(user.email());
        BirthDate birthDate = updateUserCommand.birthDate().orElse(user.birthDate());
        User newUser = new User(user.uuid(),user.userId(), user.name(), user.surname(), newEmail, birthDate);
        return this.userRepositoryPort.updateUser(newUser);
    }

    private RuntimeException throwDomainException(List<RuntimeException> exceptions){
        UserDomainException userDomainException = new UserDomainException();
        userDomainException.setRuntimeExceptions(exceptions);
        return userDomainException;
    }
}
