package com.ld.infrastructure.mapper;

import com.ld.infrastructure.db.entity.UserEntity;
import ld.domain.user.User;
import ld.domain.user.information.*;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDomainEntityMapper {
    public User userEntityToUser(UserEntity userEntity) {
        return new User(userEntity.getKeycloakId(), new UserId(userEntity.getId()), new Name(userEntity.getName()),
                new Surname(userEntity.getSurname()), new Email(userEntity.getEmail()),
                new BirthDate(userEntity.getBirthdate()));
    }
    public UserEntity userUpdateToUserEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(Optional.ofNullable(user.userId())
                .map(UserId::value).orElse(userEntity.getId()));
        userEntity.setName(user.name().value());
        userEntity.setSurname(user.surname().value());
        userEntity.setEmail(user.email().value());
        userEntity.setBirthdate(user.birthDate().value());
        userEntity.setKeycloakId(user.uuid());
        return userEntity;
    }
}
