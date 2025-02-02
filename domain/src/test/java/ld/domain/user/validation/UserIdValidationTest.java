package ld.domain.user.validation;

import ld.domain.user.exception.InvalidUserIdException;
import ld.domain.user.information.UserId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

public class UserIdValidationTest {

    @InjectMocks
    UserIdValidation userIdValidation;
    @BeforeEach
    public void beforeTest(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void id_null_should_throw_invalid_userid_exception(){
        UUID userId = null;


        InvalidUserIdException exception1 = Assertions.assertThrows(InvalidUserIdException.class,
                () -> this.userIdValidation.validate(userId));

        Assertions.assertEquals("user.validation.idNull", exception1.getMessage());
    }


    @Test
    public void id_zero_should_throw_invalid_userid_exception(){
        UUID userId = new UUID(0L, 0L);
        InvalidUserIdException exception = Assertions.assertThrows(InvalidUserIdException.class,
                () -> this.userIdValidation.validate(userId));
        Assertions.assertEquals("user.validation.idZero", exception.getMessage());
    }

    @Test
    public void id_valid_should_not_throw_exception(){
        Assertions.assertDoesNotThrow(() -> this.userIdValidation.validate(UUID.randomUUID()));
    }
}
