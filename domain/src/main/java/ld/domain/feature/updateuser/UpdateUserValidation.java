package ld.domain.feature.updateuser;

import ld.domain.user.exception.UserDomainException;
import ld.domain.user.User;
import ld.domain.user.information.BirthDate;
import ld.domain.user.information.Email;
import ld.domain.user.validation.BirthDateValidation;
import ld.domain.user.validation.EmailValidation;
import ld.domain.user.validation.UserIdValidation;
import ld.utilies.ValidationExecutor;

import java.util.List;

public class UpdateUserValidation {
    public void validate(UpdateUserCommand updateUserCommand, User user, List<RuntimeException> exceptions){

        ValidationExecutor.collectErrorFromMethod(exceptions, new UserIdValidation(),
                (validator) -> validator.validate(updateUserCommand.userId()));

        updateUserCommand.email().ifPresent(email -> this.validateEmail(email, user.email(), exceptions));

        updateUserCommand.birthDate().ifPresent(birthDate -> this.validateBirthDate(birthDate, exceptions));

        if(!exceptions.isEmpty()){
            UserDomainException userDomainException = new UserDomainException();
            userDomainException.setRuntimeExceptions(exceptions);
            throw userDomainException;
        }
    }

    private void validateEmail(Email newEmail, Email actualEmail, List<RuntimeException> exceptions){
        ValidationExecutor.collectErrorFromMethod(exceptions, new EmailValidation(),
                emailValidation ->
                        emailValidation.checkIfNewEmailEqualsOld(newEmail, actualEmail));

    }
    private void validateBirthDate(BirthDate birthDate, List<RuntimeException> exceptions){
        ValidationExecutor.collectErrorFromMethod(exceptions, new BirthDateValidation(),
                birthDateValidation -> birthDateValidation.validate(birthDate));
    }
}
