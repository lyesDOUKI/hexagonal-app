package ld.domain.feature.registeruser;


import ld.domain.user.User;
import ld.domain.user.information.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegisterUserServiceTest {

    @InjectMocks
    private RegisterUserService createUserService;

    @Mock
    private RetrieveUserByEmailPort retrieveUserByEmailPort;

    @Mock
    private PersistUserPort persistUserPort;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void saveUser_should_save_user_when_valid() {
        CreateUserCommand createUserCommand = new CreateUserCommand(new Name("test"),
                new Surname("testSurname"), new Email("test@test.com"),
                new BirthDate(LocalDate.of(2007,9,30)));
        when(this.retrieveUserByEmailPort.getUserByEmail(createUserCommand.email()))
                .thenReturn(Optional.ofNullable(null));
        this.createUserService.execute(createUserCommand);

        User validUser = new User(createUserCommand);
        verify(persistUserPort, times(1)).saveUser(validUser);
    }

    @Test
    void saveUser_should_not_save_user_when_validation_error() {
        CreateUserCommand createUserCommand = new CreateUserCommand(new Name("test"),
                new Surname("test"), new Email(""), new BirthDate(LocalDate.now()));

        assertThrows(RuntimeException.class,
                () -> createUserService.execute(createUserCommand));
        User user = new User(createUserCommand);
        verify(persistUserPort, never()).saveUser(user);
    }
}