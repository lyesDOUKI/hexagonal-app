package ld.domain.user.validation;

import ld.domain.user.exception.InvalidBirthDateException;
import ld.domain.user.information.BirthDate;

import java.time.LocalDate;
import java.time.Period;

public class BirthDateValidation {

    public void validate(BirthDate birthDate) {
        if(!validateBirthDateEnough(birthDate)){
            throw new InvalidBirthDateException("user.birthdate.validation");
        }
    }

    private boolean validateBirthDateEnough(BirthDate birthDate){
        Period period = Period.between(birthDate.value(), LocalDate.now());
        return period.getYears() > 15 || (period.getYears() == 15 && period.getMonths()
                == 0 && period.getDays() == 0);
    }

}
