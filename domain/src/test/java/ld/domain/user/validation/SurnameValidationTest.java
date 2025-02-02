package ld.domain.user.validation;

import ld.domain.user.exception.InvalidSurnameException;
import ld.domain.user.information.Surname;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class SurnameValidationTest {

    @InjectMocks
    SurnameValidation surnameValidation;
    @BeforeEach
    public void beforeTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void surname_blank_should_throw_invalid_surname_exception(){
        Surname surname = new Surname("");
        InvalidSurnameException exception = Assertions.assertThrows(InvalidSurnameException.class,
                () -> this.surnameValidation.validate(surname));
        Assertions.assertEquals("user.validation.surnameBlank", exception.getMessage());
    }

    @Test
    public void surname_valide_should_not_throw_runtime_exception(){
        Surname surname = new Surname("test");
        Assertions.assertDoesNotThrow((() -> this.surnameValidation.validate(surname)));
    }
}
