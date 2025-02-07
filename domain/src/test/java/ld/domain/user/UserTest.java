package ld.domain.user;


import ld.domain.feature.registeruser.CreateUserCommand;
import ld.domain.user.information.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

class UserTest {

    @Test
    public void user_command_should_initialize_fields_correctly() {
        CreateUserCommand createUserCommand = new
                CreateUserCommand(UUID.randomUUID(),new Name("test"), new Surname("test"),
                    new Email("test@test.com"), new BirthDate(LocalDate.now()));
        User user = new User(createUserCommand);
        Assertions.assertEquals("test", user.name().value());
        Assertions.assertEquals("test", user.surname().value());
        Assertions.assertEquals("test@test.com", user.email().value());
    }

}