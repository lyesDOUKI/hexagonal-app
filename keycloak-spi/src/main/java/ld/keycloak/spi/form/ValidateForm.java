package ld.keycloak.spi.form;

import jakarta.ws.rs.core.MultivaluedMap;
import ld.domain.user.information.BirthDate;
import ld.domain.user.validation.BirthDateValidation;
import org.keycloak.authentication.FormAction;
import org.keycloak.authentication.FormContext;
import org.keycloak.authentication.ValidationContext;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.utils.FormMessage;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import static ld.keycloak.spi.event.SpiUserProvider.formatter;

public class ValidateForm implements FormAction {
    private KeycloakSession session;
    public ValidateForm(KeycloakSession session){
        this.session = session;
    }
    @Override
    public void buildPage(FormContext formContext, LoginFormsProvider loginFormsProvider) {

    }

    @Override
    public void validate(ValidationContext validationContext) {
        System.out.println("BEGIN VALIDATE");
        MultivaluedMap<String, String> formData = validationContext.getHttpRequest().getDecodedFormParameters();
        String date = formData.getFirst("birthdate");
        BirthDateValidation validation = new BirthDateValidation();
        LocalDate localDate = null;
        try {
            localDate = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Erreur de conversion : " + e.getMessage());
        }
        try {
            validation.validate(new BirthDate(localDate));
            System.out.println("VALIDATION OK");
            validationContext.success();
        }catch (Exception e){
            System.err.println("VALIDATION KO");
            validationContext.error("birthdateRequired");
            validationContext.validationError(formData, List.of(new FormMessage("birthdate",
                    "age.petit")));
        }
    }

    @Override
    public void success(FormContext formContext) {

    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {
        return false;
    }

    @Override
    public void setRequiredActions(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {

    }

    @Override
    public void close() {

    }
}
