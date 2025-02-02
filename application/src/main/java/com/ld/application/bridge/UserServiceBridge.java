package com.ld.application.bridge;

import ld.domain.dependencies.UserRepositoryPort;
import ld.domain.feature.registeruser.RegisterUserService;
import ld.domain.feature.registeruser.RegisterUserUseCase;
import ld.domain.feature.retrieveuser.GetUserService;
import ld.domain.feature.retrieveuser.GetUserUseCase;
import ld.domain.feature.updateuser.UpdateUserService;
import ld.domain.feature.updateuser.UpdateUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceBridge {


    @Bean
    public RegisterUserUseCase registerUserUseCase(UserRepositoryPort userRepositoryPort){
        return new RegisterUserService(userRepositoryPort);
    }

    @Bean
    public GetUserUseCase getUserUseCase(UserRepositoryPort userRepositoryPort){
        return new GetUserService(userRepositoryPort);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(UserRepositoryPort userRepositoryPort){
        return new UpdateUserService(userRepositoryPort);
    }

}
