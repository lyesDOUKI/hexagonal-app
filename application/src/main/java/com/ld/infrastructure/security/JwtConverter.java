package com.ld.infrastructure.security;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.security.oauth2.jwt.Jwt;
@Component
public class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private static final String REALM_ACCESS = "realm_access";
    private static final String ROLES = "roles";
    private static final String ROLE_PREFIX = "ROLE_";

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter
            = new JwtGrantedAuthoritiesConverter();


    private final JwtProperties jwtProperties;
    @Autowired
    public JwtConverter(JwtProperties jwtProperties){
        this.jwtProperties = jwtProperties;
    }
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities =
                Stream.concat(Objects.requireNonNull(this.jwtGrantedAuthoritiesConverter.convert(jwt))
                        .stream(),
                        extractRoles(jwt).stream()
                ).collect(Collectors.toSet());
        return new JwtAuthenticationToken(jwt, authorities, getPrincipalClaimName(jwt));
    }

    public String getPrincipalClaimName(Jwt jwt){
        String claimName = Objects.nonNull(this.jwtProperties.getPrincipalAttribute())
                ? jwtProperties.getPrincipalAttribute() : JwtClaimNames.SUB;
        return jwt.getClaim(claimName);
    }

    private Collection<? extends GrantedAuthority> extractRoles(Jwt jwt){
        return Optional.ofNullable(jwt.<Map<String, Object>>getClaim(REALM_ACCESS))
                .map(realmAccess -> realmAccess.get(ROLES))
                .filter(obj -> obj instanceof Collection<?>)
                .map(obj -> (Collection<?>) obj)
                .map(roles -> roles.stream()
                        .filter(role -> role instanceof String)
                        .map(role -> (String) role)
                        .map(this::createGrantedAuthority)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
    private GrantedAuthority createGrantedAuthority(String role) {
        return new SimpleGrantedAuthority(ROLE_PREFIX + role.toUpperCase());
    }

    public Jwt getJwtFromAuthentication(@Nonnull Authentication authentication){
        return  (Jwt) authentication.getPrincipal();
    }
}
