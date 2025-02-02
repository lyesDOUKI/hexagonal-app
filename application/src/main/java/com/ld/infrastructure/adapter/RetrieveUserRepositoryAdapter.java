package com.ld.infrastructure.adapter;

import com.ld.infrastructure.mapper.UserDomainEntityMapper;
import com.ld.infrastructure.db.repository.IUserRepository;
import ld.domain.feature.retrieveuser.RetrieveUserPort;
import ld.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RetrieveUserRepositoryAdapter implements RetrieveUserPort {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private UserDomainEntityMapper userConverter;

    @Override
    public Optional<User> getUserById(UUID userId) {
        return this.userRepository.findByKeycloakId(userId)
                .map(userEntity -> this.userConverter.userEntityToUser(userEntity));
    }



}
