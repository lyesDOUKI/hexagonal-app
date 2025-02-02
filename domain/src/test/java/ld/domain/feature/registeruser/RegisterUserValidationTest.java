package ld.domain.feature.registeruser;

import ld.domain.user.exception.UserDomainException;
import ld.domain.user.information.BirthDate;
import ld.domain.user.information.Email;
import ld.domain.user.information.Name;
import ld.domain.user.information.Surname;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegisterUserValidationTest {

    @InjectMocks
    private RegisterUserValidation registerUserValidation;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_throw_domain_exception_when_bad_command(){
        //regle 1 : nom = prenom
        CreateUserCommand createUserCommand = new CreateUserCommand(new Name("test"),
                new Surname("test"), new Email("tes@test.com"), new BirthDate(LocalDate.of(2010, 2, 1)));
        List<RuntimeException> runtimeExceptionList = new ArrayList<>();
        assertThrows(UserDomainException.class, () -> registerUserValidation.validate(createUserCommand, runtimeExceptionList));
        assertFalse(runtimeExceptionList.isEmpty());
    }
}