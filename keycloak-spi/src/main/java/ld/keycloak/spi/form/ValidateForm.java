package ld.keycloak.spi.form;

import jakarta.ws.rs.core.MultivaluedMap;
import ld.domain.feature.registeruser.CreateUserCommand;
import ld.domain.feature.registeruser.RegisterUserValidation;
import ld.domain.user.exception.UserDomainException;
import ld.keycloak.common.AvailableKeycloakMessageEnum;
import ld.keycloak.common.FieldEnum;
import ld.keycloak.common.MessageUtils;
import ld.keycloak.common.UserMapper;
import org.keycloak.authentication.FormAction;
import org.keycloak.authentication.FormContext;
import org.keycloak.authentication.ValidationContext;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.utils.FormMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ValidateForm implements FormAction {

    private static final Logger LOGGER = Logger.getLogger(ValidateForm.class.getName());
    private KeycloakSession session;
    private final RegisterUserValidation registerUserValidation;
    public ValidateForm(KeycloakSession session){
        this.session = session;
        this.registerUserValidation = new RegisterUserValidation();
    }
    @Override
    public void buildPage(FormContext formContext, LoginFormsProvider loginFormsProvider) {

    }

    @Override
    public void validate(ValidationContext validationContext) {
        LOGGER.info("On validation SPI");
        MultivaluedMap<String, String> formData = validationContext.getHttpRequest().getDecodedFormParameters();
        CreateUserCommand userCommand = UserMapper.buildUserCommand(formData);
        List<RuntimeException> errors = new ArrayList<>();
        try {
            LOGGER.log(Level.INFO,"Start validation for user named : {0}", userCommand.name());
            this.registerUserValidation.validate(userCommand, errors);
            LOGGER.info("Validation OK");
            validationContext.success();
        }catch (Exception e){
            LOGGER.log(Level.SEVERE, "Error validation for user named : {0}", userCommand.name());
            LOGGER.log(Level.SEVERE, "Exception message : {0}", e.getMessage());
            if(e instanceof UserDomainException userDomainException){
                LOGGER.log(Level.SEVERE, "It's a domain exception, errors : ");
                userDomainException.getRuntimeExceptions().forEach(ex ->
                        LOGGER.log(Level.SEVERE, MessageUtils.getMessage(ex.getMessage()))
                        );
                validationContext.error("validationError");
                List<FormMessage> formMessages = userDomainException.getRuntimeExceptions().stream()
                        .map(ex ->
                                new FormMessage(FieldEnum.getFieldFromException(ex),
                                        AvailableKeycloakMessageEnum.isMessageManagedByKeycloak(ex.getMessage())
                                        ? ex.getMessage() : MessageUtils.getMessage(ex.getMessage())
                        ))
                        .toList();
                validationContext.validationError(formData, formMessages);

            }
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
