package com.ld.application.mapper;

import com.ld.application.request.CreateUserRequest;
import com.ld.application.request.UpdateUserRequest;
import com.ld.application.response.CreateUserResponse;
import com.ld.application.response.GetUserResponse;
import com.ld.application.response.UpdateUserResponse;
import ld.domain.feature.registeruser.CreateUserCommand;
import ld.domain.feature.updateuser.UpdateUserCommand;
import ld.domain.user.User;
import ld.domain.user.information.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserDomainApiMapper {
    public static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public CreateUserResponse userToCreateUserResponse(User user){
        return new CreateUserResponse(user.userId().value(), user.name().value(),
                user.email().value());
    }

    public GetUserResponse userToGetUserResponse(User user){
        return new GetUserResponse(user.userId().value(), user.name().value(),
                user.email().value(), user.birthDate().value().format(FORMATTER));
    }
    public UpdateUserResponse userToUpdateUserResponse(User user){
        return new UpdateUserResponse(user.name().value(), user.email().value());
    }
    public CreateUserCommand userRequestToCommand(CreateUserRequest createUserRequest){
        return  new CreateUserCommand(new Name(createUserRequest.name()),
                new Surname(createUserRequest.surname()), new Email(createUserRequest.email())
                , new BirthDate(LocalDate.parse(createUserRequest.birthdate())));
    }
    public UpdateUserCommand userRequestToCommand(UpdateUserRequest updateUserRequest, UUID id){
        return new UpdateUserCommand(id,
                Optional.ofNullable(updateUserRequest.email()).map(Email::new),
                Optional.ofNullable(updateUserRequest.birthdate())
                        .map(LocalDate::parse)
                        .map(BirthDate::new));
    }
}
