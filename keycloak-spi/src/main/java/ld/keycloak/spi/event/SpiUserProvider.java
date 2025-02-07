package ld.keycloak.spi.event;

import ld.domain.feature.registeruser.*;
import ld.domain.user.User;
import ld.domain.user.exception.UserDomainException;
import ld.keycloak.common.MessageUtils;
import ld.keycloak.common.UserMapper;
import ld.keycloak.spi.event.adapter.RegisterUserAdapter;
import ld.keycloak.spi.event.adapter.RetreiveUserAdapter;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.*;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class SpiUserProvider implements EventListenerProvider {

    // Static logger declaration
    private static final Logger LOGGER = Logger.getLogger(SpiUserProvider.class.getName());

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final KeycloakSession session;
    private final RegisterUserUseCase registerUserUseCase;
    private final PersistUserPort persistUserPort = new RegisterUserAdapter();
    private final RetrieveUserByEmailPort retrieveUserByEmailPort = new RetreiveUserAdapter();

    public SpiUserProvider(KeycloakSession session) {
        this.session = session;
        this.registerUserUseCase = new RegisterUserService(persistUserPort, retrieveUserByEmailPort);
    }

    public boolean removeUser(RealmModel realmModel, UserModel userModel) {
        LOGGER.info("Attempting to delete user: " + userModel.getUsername());
        return this.session.users().removeUser(realmModel, userModel);
    }

    @Override
    public void onEvent(Event event) {
        LOGGER.info("Received event, Event Type: " + event.getType().name());

        RealmModel realmModel = null;
        UserModel user = null;

        if (event.getType() == EventType.REGISTER) {
            LOGGER.info("Event Type is REGISTER. Starting processing...");

            realmModel = session.realms().getRealm(event.getRealmId());
            user = session.users().getUserById(realmModel, event.getUserId());

            if (user != null) {
                LOGGER.info("User found, processing user: " + user.getUsername());

                try {
                    CreateUserCommand userCommand = UserMapper.buildUserDomain(user);
                    LOGGER.info("Executing user registration for: " + userCommand.name());

                    User userPersisted = registerUserUseCase.execute(userCommand);
                    LOGGER.info("User successfully registered in DB");

                } catch (Exception e) {
                    LOGGER.severe("Error during user registration: " + e.getMessage());
                    removeUser(realmModel, user);

                    if (e instanceof UserDomainException userDomainException) {
                        LOGGER.severe("Domain exception occurred during registration:");
                        userDomainException.getRuntimeExceptions().forEach(exception ->
                                LOGGER.severe(MessageUtils.getMessage(exception.getMessage()))
                        );
                    }
                }
            } else {
                LOGGER.warning("User not found for event with ID: " + event.getUserId());
            }
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {
        LOGGER.info("Admin event operation: " + adminEvent.getOperationType().name());
    }

    @Override
    public void close() {
        LOGGER.info("Closing SpiUserProvider.");
    }
}
