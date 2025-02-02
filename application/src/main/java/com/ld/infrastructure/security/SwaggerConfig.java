package com.ld.infrastructure.security;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API du User Service - Authentification via Keycloak",
                version = "1.0"
        )
)
@SecurityScheme(
        name = "keycloak",
        scheme = "bearer",
        openIdConnectUrl = "${keycloak.token-uri}",
        type = SecuritySchemeType.OAUTH2,
        in = SecuritySchemeIn.HEADER,
        bearerFormat = "JWT",
        flows = @OAuthFlows(
                implicit = @OAuthFlow(
                        authorizationUrl = "${keycloak.auth-server-url}",
                        scopes = {
                                @OAuthScope(name = "openid", description = "OpenID Connect")
                        }
                )
        )
)
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("keycloak"));
    }
}