package com.ld.infrastructure.adapter;

import com.ld.infrastructure.mapper.UserDomainEntityMapper;
import com.ld.infrastructure.db.entity.UserEntity;
import com.ld.infrastructure.db.repository.IUserRepository;
import ld.domain.dependencies.UserRepositoryPort;
import ld.domain.user.User;
import ld.domain.user.information.Email;
import ld.domain.user.information.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private UserDomainEntityMapper userConverter;
    @Override
    public User saveUser(User user) {
        UserEntity userEntity = this.userRepository.save(this.userConverter.userCreateToUserEntity(user));
        return this.userConverter.userEntityToUser(userEntity);
    }

    @Override
    public Optional<User> getUserById(UUID userId) {
        return this.userRepository.findByKeycloakId(userId)
                .map(userEntity -> this.userConverter.userEntityToUser(userEntity));
    }

    @Override
    public Optional<User> getUserByEmail(Email email) {
        return this.userRepository.findByEmail(email.value())
                .map(userEntity -> this.userConverter.userEntityToUser(userEntity));
    }

    @Override
    public User updateUser(User user) {
        UserEntity userEntity = this.userRepository.save(this.userConverter.userUpdateToUserEntity(user));
        return this.userConverter.userEntityToUser(userEntity);
    }
}
