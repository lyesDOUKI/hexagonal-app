package com.ld.infrastructure.adapter;

import com.ld.infrastructure.db.entity.UserEntity;
import com.ld.infrastructure.db.repository.IUserRepository;
import com.ld.infrastructure.mapper.UserDomainEntityMapper;
import ld.domain.feature.updateuser.UpdateUserPort;
import ld.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateUserRepositoryAdapter implements UpdateUserPort {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private UserDomainEntityMapper userConverter;
    @Override
    public User updateUser(User user) {
        UserEntity userEntity = this.userRepository.save(this.userConverter.userUpdateToUserEntity(user));
        return this.userConverter.userEntityToUser(userEntity);
    }
}
