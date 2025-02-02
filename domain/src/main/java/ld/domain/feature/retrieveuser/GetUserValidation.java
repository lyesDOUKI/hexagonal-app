package ld.domain.feature.retrieveuser;

import ld.domain.user.exception.UserDomainException;
import ld.domain.user.validation.UserIdValidation;
import ld.utilies.ValidationExecutor;

import java.util.ArrayList;
import java.util.List;

public class GetUserValidation {

    public void validate(GetUserByIdQuery userByIdQuery){
        List<RuntimeException> errors = new ArrayList<>();
       ValidationExecutor.collectErrorFromMethod(errors, new UserIdValidation(),
               validation -> validation.validate(userByIdQuery.userId()));
        if(!errors.isEmpty()){
            UserDomainException userDomainException = new UserDomainException();
            userDomainException.setRuntimeExceptions(errors);
            throw userDomainException;
        }
    }
}
