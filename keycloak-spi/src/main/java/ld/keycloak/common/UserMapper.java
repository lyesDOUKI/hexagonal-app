package ld.keycloak.common;

import jakarta.ws.rs.core.MultivaluedMap;
import ld.domain.feature.registeruser.CreateUserCommand;
import ld.domain.user.information.BirthDate;
import ld.domain.user.information.Email;
import ld.domain.user.information.Name;
import ld.domain.user.information.Surname;
import org.keycloak.models.UserModel;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.UUID;

import static ld.keycloak.spi.event.SpiUserProvider.formatter;

public class UserMapper {

    public static CreateUserCommand buildUserCommand(MultivaluedMap<String, String> formData){
        Name name = new Name(formData.getFirst("firstName"));
        Surname surname = new Surname(formData.getFirst("lastName"));
        Email email = new Email(formData.getFirst("email"));
        String birthdateFormData = formData.getFirst("birthdate");
        LocalDate localDate = null;
        try {
            localDate = LocalDate.parse(birthdateFormData, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Erreur de conversion : " + e.getMessage());

        }
        BirthDate birthDate = new BirthDate(localDate);
        return new CreateUserCommand(null, name, surname, email, birthDate);
    }

    public static CreateUserCommand buildUserDomain(UserModel userModel){
        UUID uuid = UUID.fromString(userModel.getId());
        Surname surname = new Surname(userModel.getLastName());
        Name name = new Name(userModel.getFirstName());
        Email email = new Email(userModel.getEmail());
        String birthdateFromUerModel = userModel.getFirstAttribute("birthdate");
        LocalDate localDate = null;
        try {
            localDate = LocalDate.parse(birthdateFromUerModel, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Erreur de conversion : " + e.getMessage());

        }
        BirthDate birthDate = new BirthDate(localDate);

        return new CreateUserCommand(uuid, name, surname,email,birthDate);
    }

}
