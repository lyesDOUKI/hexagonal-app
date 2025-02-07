package com.ld.application.mapper;

import com.ld.application.request.PutAddressRequest;
import com.ld.application.response.GetUserResponse;
import ld.domain.feature.putaddress.PutAddressToUserCommand;
import ld.domain.user.User;
import ld.domain.user.information.adresse.Adresse;
import ld.domain.user.information.adresse.information.*;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

@Component
public class UserDomainApiMapper {
    public static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public GetUserResponse userToGetUserResponse(User user){
        GetUserResponse getUserResponse = new GetUserResponse(user.userId().value(), user.name().value(),
                user.email().value(), user.birthDate().value().format(FORMATTER));
        if(Objects.nonNull(user.adresse())){
            GetUserResponse.MonAdresse monAdresse = new GetUserResponse.MonAdresse(
                    user.adresse().nomAdresse().value(), user.adresse().complementAdresse().value(),
                    user.adresse().codePostal().value(), user.adresse().ville().value(),
                    user.adresse().pays().value()
            );
            getUserResponse.setAdresse(monAdresse);
        }
        return getUserResponse;
    }
    public PutAddressToUserCommand userRequestToCommand(PutAddressRequest putAddressRequest, UUID id){
        Adresse adresse = new Adresse(null, new NomAdresse(putAddressRequest.getNomAdresse()),
                new ComplementAdresse(putAddressRequest.getComplementAdresse()),
                new CodePostal(putAddressRequest.getCodePostal()), new Ville(putAddressRequest.getVille()),
                new Pays(putAddressRequest.getPays()));
        return new PutAddressToUserCommand(id, adresse);
    }
}
