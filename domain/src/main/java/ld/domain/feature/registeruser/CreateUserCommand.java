package ld.domain.feature.registeruser;

import ld.domain.user.information.*;

import java.util.Objects;

public record CreateUserCommand(Name name, Surname surname, Email email, BirthDate birthDate) {
    public CreateUserCommand{
        validateNotNull(name, "name");
        validateNotNull(surname, "surname");
        validateNotNull(email, "email");
        validateNotNull(birthDate, "birthdate");
    }
    private static <T> void validateNotNull(T value, String fieldName) {
        Objects.requireNonNull(value, fieldName + " can't be null on CreateUserCommand");
    }
}
