package com.ld.infrastructure.configuration;

import com.ld.infrastructure.adapter.UserRepositoryAdapter;
import ld.domain.dependencies.UserRepositoryPort;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class InfrastructureConfiguration {


    @Bean
    public UserRepositoryPort userRepositoryPort(){
        return new UserRepositoryAdapter();
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
