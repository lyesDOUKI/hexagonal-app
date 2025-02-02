package ld.domain.user.validation;

import ld.domain.user.exception.InvalidEmailException;
import ld.domain.user.information.Email;

import java.util.Objects;

public class EmailValidation {

    public void validate(Email email) {
        if(isEmailBlank(email)){
            throw new InvalidEmailException("user.validation.emailBlank");
        }
    }
    private boolean isEmailBlank(Email email){
        return email.value().isBlank();
    }

    public void checkIfNewEmailEqualsOld(Email email1, Email email2){
        if(Objects.nonNull(email1) && Objects.nonNull(email2)){
            if(email1.value().equalsIgnoreCase(email2.value())){
                throw new InvalidEmailException("update.user.validation.sameEmail");
            }
        }

    }
}
