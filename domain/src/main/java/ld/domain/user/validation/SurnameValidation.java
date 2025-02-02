package ld.domain.user.validation;

import ld.domain.user.exception.InvalidSurnameException;
import ld.domain.user.information.Surname;

public class SurnameValidation {

    public void validate(Surname surname){
        if(isSurnameBlank(surname)){
            throw new InvalidSurnameException("user.validation.surnameBlank");
        }
    }
    private boolean isSurnameBlank(Surname surname){
        return surname.value().isBlank();
    }
}
