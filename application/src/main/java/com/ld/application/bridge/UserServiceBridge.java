package com.ld.application.bridge;

import ld.domain.feature.common.RetrieveUserPort;
import ld.domain.feature.putaddress.PersistAddressPort;
import ld.domain.feature.putaddress.PutAddressService;
import ld.domain.feature.putaddress.PutAddressUseCase;
import ld.domain.feature.putaddress.VerifyAddressPort;
import ld.domain.feature.retrieveuser.GetUserService;
import ld.domain.feature.retrieveuser.GetUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceBridge {


    @Bean
    public GetUserUseCase getUserUseCase(RetrieveUserPort userRepositoryPort){
        return new GetUserService(userRepositoryPort);
    }

    @Bean
    public PutAddressUseCase updateUserUseCase(PersistAddressPort persistAddressPort,
                                               VerifyAddressPort verifyAddressPort,
                                               RetrieveUserPort retrieveUserPort){
        return new PutAddressService(verifyAddressPort, retrieveUserPort, persistAddressPort);
    }

}
