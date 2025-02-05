package ld.keycloak.spi.event;

import ld.domain.feature.registeruser.*;
import ld.domain.user.User;
import ld.domain.user.exception.UserDomainException;
import ld.domain.user.information.BirthDate;
import ld.domain.user.information.Email;
import ld.domain.user.information.Name;
import ld.domain.user.information.Surname;
import ld.keycloak.common.MessageUtils;
import ld.keycloak.spi.event.adapter.RegisterUserAdapter;
import ld.keycloak.spi.event.adapter.RetreiveUserAdapter;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.*;
import org.keycloak.sessions.CommonClientSessionModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class SpiUserProvider implements EventListenerProvider {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
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
        Surname surname = new Surname(userModel.getLastName());
        Name name = new Name(userModel.getFirstName());
        Email email = new Email(userModel.getEmail());
        String birthdateFromUerModel = userModel.getFirstAttribute("birthdate");
        System.out.println("##### Récuperation de la date de naissance : " + birthdateFromUerModel);
        LocalDate localDate = null;
        try {
            localDate = LocalDate.parse(birthdateFromUerModel, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Erreur de conversion : " + e.getMessage());

        }
        BirthDate birthDate = new BirthDate(localDate);

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
                    if(e instanceof UserDomainException userDomainException){
                        System.err.println("Exception du domain : ");
                        userDomainException.getRuntimeExceptions().forEach(exception -> System.out.println(
                                MessageUtils.getMessage(exception.getMessage())
                        ));
                    }
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
