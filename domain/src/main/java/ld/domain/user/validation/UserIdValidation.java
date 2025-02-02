package ld.domain.user.validation;

import ld.domain.user.exception.InvalidUserIdException;
import ld.domain.user.information.UserId;

import java.util.Objects;
import java.util.UUID;

public class UserIdValidation {
    public void validate(UUID input) {
        if(isUserIdNull(input)){
            throw new InvalidUserIdException("user.validation.idNull");
        }
        if(isUserIdZero(input)){
            throw new InvalidUserIdException("user.validation.idZero");
        }
    }

    private boolean isUserIdNull(UUID userId){
        return Objects.isNull(userId);
    }
    private boolean isUserIdZero(UUID userId){
        return userId.equals(new UUID(0L, 0L));
    }
}
