package com.ld.application.bridge;

import ld.domain.feature.retrieveuser.RetrieveUserPort;
import ld.domain.feature.updateuser.UpdateUserPort;
import ld.domain.feature.retrieveuser.GetUserService;
import ld.domain.feature.retrieveuser.GetUserUseCase;
import ld.domain.feature.updateuser.UpdateUserService;
import ld.domain.feature.updateuser.UpdateUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceBridge {


    @Bean
    public GetUserUseCase getUserUseCase(RetrieveUserPort userRepositoryPort){
        return new GetUserService(userRepositoryPort);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(UpdateUserPort updateUserPort,
                                               RetrieveUserPort retrieveUserPort){
        return new UpdateUserService(updateUserPort, retrieveUserPort);
    }

}
