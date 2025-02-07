package com.ld.infrastructure.adapter;

import com.ld.infrastructure.db.entity.UserAddress;
import com.ld.infrastructure.db.entity.UserEntity;
import com.ld.infrastructure.db.repository.IUserAddressRepository;
import com.ld.infrastructure.db.repository.IUserRepository;
import com.ld.infrastructure.mapper.UserDomainEntityMapper;
import ld.domain.feature.putaddress.PersistAddressPort;
import ld.domain.user.User;
import ld.domain.user.information.UserId;
import ld.domain.user.information.adresse.Adresse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PutAddressUserRepositoryAdapter implements PersistAddressPort {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private UserDomainEntityMapper userConverter;
    @Autowired
    IUserAddressRepository iUserAddressRepository;

    @Override
    public User execute(Adresse adresse, UserId userId) {
        Optional<UserAddress> optionalUserAddress = this.iUserAddressRepository
                .findByNomAdresseAndComplementAdresseAndCodePostal(adresse.nomAdresse().value().toUpperCase(),
                        adresse.complementAdresse().value().toUpperCase(),adresse.codePostal().value());
        UserAddress userAddress = optionalUserAddress.orElseGet(() ->this.createUserAdresse(adresse));
        Optional<UserEntity> optionalUserEntity = this.userRepository.findById(userId.value());
        UserEntity userEntity = optionalUserEntity.orElseThrow(() -> new RuntimeException("User not found to put the adress"));
        userEntity.setAdresse(userAddress);
        return this.userConverter.userEntityToUser(this.userRepository.save(userEntity));
    }

    private UserAddress createUserAdresse(Adresse adresse){
        UserAddress userAddress = this.userConverter.AdresseToUserAddress(adresse);
        this.iUserAddressRepository.save(userAddress);
        return userAddress;
    }
}
