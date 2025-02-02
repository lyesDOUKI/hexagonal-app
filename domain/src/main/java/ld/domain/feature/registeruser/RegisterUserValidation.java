package ld.domain.feature.registeruser;

import ld.domain.user.exception.UserDomainException;
import ld.domain.user.validation.BirthDateValidation;
import ld.domain.user.validation.EmailValidation;
import ld.domain.user.validation.NameValidation;
import ld.domain.user.validation.SurnameValidation;
import ld.utilies.ValidationExecutor;

import java.util.List;

public class RegisterUserValidation {

    public void validate(CreateUserCommand createUserCommand, List<RuntimeException> runtimeExceptionList){

        ValidationExecutor.collectErrorFromMethod(runtimeExceptionList, new NameValidation(),
                validator -> validator.validate(createUserCommand.name()));
        ValidationExecutor.collectErrorFromMethod(runtimeExceptionList, new SurnameValidation(),
                validator -> validator.validate(createUserCommand.surname()));

        ValidationExecutor.collectErrorFromMethod(runtimeExceptionList, new NameValidation(),
                nameValidation ->
                        nameValidation.validateNameNotEqualSurname(createUserCommand.name(), createUserCommand.surname()));

        ValidationExecutor.collectErrorFromMethod(runtimeExceptionList, new EmailValidation(),
                validator -> validator.validate(createUserCommand.email()));
        ValidationExecutor.collectErrorFromMethod(runtimeExceptionList, new BirthDateValidation(),
                validator -> validator.validate(createUserCommand.birthDate()));

        if(!runtimeExceptionList.isEmpty()){
            UserDomainException userDomainException = new UserDomainException();
            userDomainException.setRuntimeExceptions(runtimeExceptionList);
            throw userDomainException;
        }
    }
}
