package ld.domain.user.validation;

import ld.domain.user.exception.InvalidNameException;
import ld.domain.user.information.Name;
import ld.domain.user.information.Surname;

import java.util.Objects;

public class NameValidation {
    public void validate(Name name) {
        if(isNameBlank(name)){
            throw new InvalidNameException("user.validation.nameBlank");
        }
    }
    private boolean isNameBlank(Name name){
        return name.value().isBlank();
    }

    public void validateNameNotEqualSurname(Name name, Surname surname){
        if(name.value().equalsIgnoreCase(surname.value())){
            throw new InvalidNameException("user.validation.sameNameUsername");
        }
    }
}
