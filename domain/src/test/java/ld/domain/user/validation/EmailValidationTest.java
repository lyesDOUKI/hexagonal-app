package ld.domain.user.validation;

import ld.domain.user.exception.InvalidEmailException;
import ld.domain.user.information.Email;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class EmailValidationTest {
    @InjectMocks
    EmailValidation emailValidation;

    @BeforeEach
    public void beforeTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void email_blank_should_throw_email_exception(){
        Email email = new Email("");
        InvalidEmailException exception = Assertions.assertThrows(InvalidEmailException.class,
                () -> this.emailValidation.validate(email));
        Assertions.assertEquals("user.validation.emailBlank", exception.getMessage());
    }

    @Test
    public void email_valide_should_not_throw_runtime_exception(){
        Email email = new Email("test@test.com");
        Assertions.assertDoesNotThrow((() -> this.emailValidation.validate(email)));
    }
}
