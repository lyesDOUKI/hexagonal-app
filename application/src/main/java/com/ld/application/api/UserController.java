package com.ld.application.api;

import com.ld.application.mapper.UserDomainApiMapper;
import com.ld.application.request.PutAddressRequest;
import com.ld.application.response.GetUserResponse;
import com.ld.infrastructure.security.JwtConverter;
import com.ld.infrastructure.security.TokenBlackListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import ld.domain.feature.putaddress.PutAddressToUserCommand;
import ld.domain.feature.putaddress.PutAddressUseCase;
import ld.domain.feature.retrieveuser.GetUserByIdQuery;
import ld.domain.feature.retrieveuser.GetUserUseCase;
import ld.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping(value = "/user", produces = {MediaType.APPLICATION_JSON_VALUE})
@Tag(name = "User Management", description = "Operations related to user management")
public class UserController {

    @Value("${keycloak.server}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    private final PutAddressUseCase putAddressUseCase;
    private final GetUserUseCase getUserService;
    private final UserDomainApiMapper userMapper;
    private final JwtConverter jwtConverter;
    private final TokenBlackListService tokenBlackListService;

    @Autowired
    public UserController(PutAddressUseCase putAddressUseCase,
                          GetUserUseCase getUserService, UserDomainApiMapper userMapper,
                          JwtConverter jwtConverter, TokenBlackListService tokenBlackListService) {
        this.putAddressUseCase = putAddressUseCase;
        this.getUserService = getUserService;
        this.userMapper = userMapper;
        this.jwtConverter = jwtConverter;
        this.tokenBlackListService = tokenBlackListService;
    }

    @GetMapping("/me")
    @Operation(summary = "Récupérer un utilisateur par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetUserResponse.class))),
            @ApiResponse(responseCode = "204", description = "Aucun utilisateur trouvé avec cet ID"),
            @ApiResponse(responseCode = "401", description = "Vous n'êtes actuellement pas authentifié")
    })
    public ResponseEntity<GetUserResponse> getUserById(Authentication authentication) {
        Jwt jwt = this.jwtConverter.getJwtFromAuthentication(authentication);
        String uuid = this.jwtConverter.getPrincipalClaimName(jwt);
        GetUserByIdQuery userByIdQuery = new GetUserByIdQuery(UUID.fromString(uuid));
        return getUserService.getUserById(userByIdQuery)
                .map(user -> ResponseEntity.ok(userMapper.userToGetUserResponse(user)))
                .orElse(ResponseEntity.noContent().build());
    }

    @PutMapping("/me/address")
    @Operation(summary = "Mettre à jour un utilisateur avec une adresse postal",
            description = "Cette opération va mettre à jour l'adresse d'un utilisateur dans la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Utilisateur mis à jour",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetUserResponse.class))),
            @ApiResponse(responseCode = "200", description = "Des contraintes n'ont pas été respectées pour mettre à jour un utilisateur")
    })
    public ResponseEntity<GetUserResponse> updateUser(@RequestBody
                                                      @Valid PutAddressRequest putAddressRequest, Authentication authentication) {
        Jwt jwt = this.jwtConverter.getJwtFromAuthentication(authentication);
        String uuid = this.jwtConverter.getPrincipalClaimName(jwt);
        PutAddressToUserCommand putAddressToUserCommand = this.userMapper.userRequestToCommand(putAddressRequest, UUID.fromString(uuid));
        User user = this.putAddressUseCase.execute(putAddressToUserCommand);
        return ResponseEntity.ok(userMapper.userToGetUserResponse(user));
    }

    @GetMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(Authentication authentication) {
        Jwt jwt = this.jwtConverter.getJwtFromAuthentication(authentication);
        long expiresIn = Objects.requireNonNull(jwt.getExpiresAt()).getEpochSecond() - Instant.now().getEpochSecond();
        String logoutUrl = null;
        try {
            this.tokenBlackListService.blacklistToken(jwt.getTokenValue(), expiresIn);

            logoutUrl = String.format("%s/realms/%s/protocol/openid-connect/logout?sub=%s",
                    keycloakServerUrl,
                    realm,
                    this.jwtConverter.getPrincipalClaimName(jwt));

            return ResponseEntity.ok(Map.of("logoutUrl", logoutUrl));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to process logout"));
        }
    }
}
