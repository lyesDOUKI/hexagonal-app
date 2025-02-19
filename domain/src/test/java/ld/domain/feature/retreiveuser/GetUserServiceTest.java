package ld.domain.feature.retreiveuser;

import ld.domain.feature.registeruser.CreateUserCommand;
import ld.domain.feature.retrieveuser.GetUserService;
import ld.domain.user.User;
import ld.domain.feature.common.RetrieveUserPort;
import ld.domain.feature.retrieveuser.GetUserByIdQuery;
import ld.domain.user.information.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetUserServiceTest {

    @InjectMocks
    private GetUserService getUserService;

    @Mock
    private RetrieveUserPort userRepositoryPort;


    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getUserById_should_return_user_when_id_is_valid() {
        UUID userId = UUID.randomUUID();
        CreateUserCommand createUserCommand = new  CreateUserCommand(userId, new Name("test"),
                new Surname("test"), new Email("test@example.com"), new BirthDate(LocalDate.now()));
        User expectedUser = new User(createUserCommand);
        when(userRepositoryPort.getUserById(expectedUser.uuid())).thenReturn(Optional.of(expectedUser));
        GetUserByIdQuery userByIdQuery = new GetUserByIdQuery(userId);
        Optional<User> result = getUserService.getUserById(userByIdQuery);

        assertTrue(result.isPresent());
        assertEquals(expectedUser, result.get());
        verify(userRepositoryPort, times(1)).getUserById(userByIdQuery.userId());
    }

    @Test
    void getUserById_should_not_be_called_when_id_is_null() {
        UUID nullId = null;
        GetUserByIdQuery userByIdQuery = new GetUserByIdQuery(nullId);
        assertThrows(RuntimeException.class,
                () -> getUserService.getUserById(userByIdQuery));
        verify(userRepositoryPort, never()).getUserById(userByIdQuery.userId());
    }

}
