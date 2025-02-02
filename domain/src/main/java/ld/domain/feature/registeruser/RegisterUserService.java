package ld.domain.feature.registeruser;

import ld.domain.user.exception.InvalidEmailException;
import ld.domain.user.exception.UserDomainException;
import ld.domain.user.User;
import ld.domain.dependencies.UserRepositoryPort;
import ld.domain.user.information.Email;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegisterUserService implements RegisterUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final RegisterUserValidation createUserValidation;

    public RegisterUserService(UserRepositoryPort userRepositoryPort){
        this.userRepositoryPort = userRepositoryPort;
        this.createUserValidation = new RegisterUserValidation();
    }
    @Override
    public User execute(CreateUserCommand createUserCommand) {
        List<RuntimeException> createUserExceptions = new ArrayList<>();
        this.createUserValidation.validate(createUserCommand, createUserExceptions);
        Optional<User> optionalUser = this.findUserByEmail(createUserCommand.email());
        if(optionalUser.isPresent()){
            createUserExceptions.add(new InvalidEmailException("user.create.validation.emailExist"));
            this.throwDomainException(createUserExceptions);
        }
        User user = new User(createUserCommand);
        return this.userRepositoryPort.saveUser(user);
    }
    private Optional<User> findUserByEmail(Email email){
        return this.userRepositoryPort.getUserByEmail(email);
    }
    private void throwDomainException(List<RuntimeException> createUserExceptions){
        UserDomainException userDomainException = new UserDomainException();
        userDomainException.setRuntimeExceptions(createUserExceptions );
        throw userDomainException;
    }
}
