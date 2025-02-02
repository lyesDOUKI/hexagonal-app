package ld.keycloak.spi;

import ld.domain.feature.registeruser.*;
import ld.domain.user.User;
import ld.domain.user.information.BirthDate;
import ld.domain.user.information.Email;
import ld.domain.user.information.Name;
import ld.domain.user.information.Surname;
import ld.keycloak.spi.adapter.RegisterUserAdapter;
import ld.keycloak.spi.adapter.RetreiveUserAdapter;
import org.keycloak.component.ComponentModel;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserRegistrationProvider;

import java.time.LocalDate;
import java.util.UUID;

public class SpiUserProvider implements EventListenerProvider {

    private final KeycloakSession session;
    private final RegisterUserUseCase registerUserUseCase;
    private final PersistUserPort persistUserPort = new RegisterUserAdapter();
    private final RetrieveUserByEmailPort retrieveUserByEmailPort = new RetreiveUserAdapter();

    public SpiUserProvider(KeycloakSession session){
        this.session = session;
        this.registerUserUseCase = new RegisterUserService(persistUserPort, retrieveUserByEmailPort);
    }
    public boolean removeUser(RealmModel realmModel, UserModel userModel) {
        System.out.println("delete user : " + userModel.getUsername());
        return this.session.users().removeUser(realmModel, userModel);
    }
    private CreateUserCommand buildUserDomain(UserModel userModel){
        UUID uuid = UUID.fromString(userModel.getId());
        Surname surname = new Surname(userModel.getFirstName());
        Name name = new Name(userModel.getLastName());
        Email email = new Email(userModel.getEmail());
        BirthDate birthDate = new BirthDate(LocalDate.of(2001,8,3));

        return new CreateUserCommand(uuid, name, surname,email,birthDate);
    }

    @Override
    public void onEvent(Event event) {
        System.out.println("EVENT, TYPE EVENT : " +event.getType().name());
        RealmModel realmModel = null;
        UserModel user = null;
        if (event.getType() == EventType.REGISTER) {
            System.out.println("OK, REGISTER EVENT");
            realmModel = session.realms().getRealm(event.getRealmId());
            user = session.users().getUserById(realmModel, event.getUserId());
            if (user != null) {
                System.out.println("OK, USER_MODEL NON NULL");
                try {
                    CreateUserCommand userCommand = buildUserDomain(user);
                    User userPersisted = registerUserUseCase.execute(userCommand);
                    System.out.println("Succès enregistrement utilisateur en BDD : " + userPersisted.userId().value());
                } catch (Exception e) {
                    System.err.println("Erreur lors de l'enregistrement en BDD : " + e.getMessage());
                    removeUser(realmModel, user);
                }
            }
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {
        System.out.println("EVENT ADMIN OPERATION : " + adminEvent.getOperationType().name());
    }

    @Override
    public void close() {

    }
}
