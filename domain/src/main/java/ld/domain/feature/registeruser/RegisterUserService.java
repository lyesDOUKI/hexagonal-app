package ld.domain.feature.registeruser;

import ld.domain.user.exception.InvalidEmailException;
import ld.domain.user.exception.UserDomainException;
import ld.domain.user.User;
import ld.domain.user.information.Email;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegisterUserService implements RegisterUserUseCase {

    private final PersistUserPort userRepositoryPort;
    private final RegisterUserValidation createUserValidation;
    private final RetrieveUserByEmailPort retrieveUserByEmailPort;

    public RegisterUserService(PersistUserPort userRepositoryPort, RetrieveUserByEmailPort retrieveUserByEmailPort){
        this.userRepositoryPort = userRepositoryPort;
        this.retrieveUserByEmailPort = retrieveUserByEmailPort;
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
        return this.retrieveUserByEmailPort.getUserByEmail(email);
    }
    private void throwDomainException(List<RuntimeException> createUserExceptions){
        UserDomainException userDomainException = new UserDomainException();
        userDomainException.setRuntimeExceptions(createUserExceptions );
        throw userDomainException;
    }
}
