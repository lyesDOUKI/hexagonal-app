package ld.domain.user;

import ld.domain.feature.registeruser.CreateUserCommand;
import ld.domain.user.information.*;
import ld.domain.user.information.adresse.Adresse;

import java.util.Objects;
import java.util.UUID;

public record User(UUID uuid, UserId userId, Name name, Surname surname, Email email, BirthDate birthDate, Adresse adresse) {
    public User{
        validateNotNull(name, "name");
        validateNotNull(surname, "surname");
        validateNotNull(email, "email");
    }
    public User(CreateUserCommand createUserCommand){
        this(createUserCommand.uuid(),null, createUserCommand.name(), createUserCommand.surname(),
                createUserCommand.email(), createUserCommand.birthDate(), null);
    }
    private static <T> void validateNotNull(T value, String fieldName) {
        Objects.requireNonNull(value, fieldName + " can't be null on User Model");
    }
}
