package com.ld.infrastructure.mapper;

import com.ld.infrastructure.db.entity.UserAddress;
import com.ld.infrastructure.db.entity.UserEntity;
import ld.domain.user.User;
import ld.domain.user.information.*;
import ld.domain.user.information.adresse.Adresse;
import ld.domain.user.information.adresse.information.*;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class UserDomainEntityMapper {
    public User userEntityToUser(UserEntity userEntity) {
        return new User(userEntity.getKeycloakId(), new UserId(userEntity.getId()), new Name(userEntity.getName()),
                new Surname(userEntity.getSurname()), new Email(userEntity.getEmail()),
                new BirthDate(userEntity.getBirthdate()), Objects.nonNull(userEntity.getAdresse())
                ? this.UserAddressToAddress(userEntity.getAdresse(), new UserId(userEntity.getId()))
                : null);
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

    public Adresse UserAddressToAddress(UserAddress userAddress, UserId userId){
        return new Adresse(userId, new NomAdresse(userAddress.getNomAdresse()), new ComplementAdresse(userAddress.getComplementAdresse()),
                new CodePostal(userAddress.getCodePostal()), new Ville(userAddress.getVille()),
                new Pays(userAddress.getPays()));
    }

    public UserAddress AdresseToUserAddress(Adresse adresse){
        UserAddress userAddress = new UserAddress();
        userAddress.setNomAdresse(adresse.nomAdresse().value());
        userAddress.setComplementAdresse(adresse.complementAdresse().value());
        userAddress.setCodePostal(adresse.codePostal().value());
        userAddress.setVille(adresse.ville().value());
        userAddress.setPays(adresse.pays().value());
        return userAddress;
    }
}
