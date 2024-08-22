package com.devops.user_service_api_1.service.impl;

import com.devops.user_service_api_1.dto.request.RequestSystemUserDto;
import com.devops.user_service_api_1.entity.SystemUser;
import com.devops.user_service_api_1.exception.DuplicateEntryException;
import com.devops.user_service_api_1.exception.EntryNotFoundException;
import com.devops.user_service_api_1.repo.SystemUserRepo;
import com.devops.user_service_api_1.service.SystemUserService;
import com.devops.user_service_api_1.util.KeycloakSecurityUtil;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SystemUserServiceImpl implements SystemUserService {

    private final KeycloakSecurityUtil keycloakSecurityUtil;
    private final SystemUserRepo systemUserRepo;

    @Value("${keycloak.config.realm}")
    private String realm;
    @Value("${keycloak.config.client-id}")
    private String clientId;
    @Value("${keycloak.config.secret}")
    private String secret;
    @Value("${spring.security.oauth2.resourceserver.jwt.token-uri}")
    private String keyCloakApiUrl;

    @Override
    public void signup(RequestSystemUserDto dto) {
        String userId;
        Keycloak keycloak = null;

        UserRepresentation existingUser = null;
        keycloak = keycloakSecurityUtil.getKeycloakInstance();

        existingUser = keycloak.realm(realm).users().search(dto.getEmail()).stream().findFirst().orElse(null);

        if (existingUser != null) {
            Optional<SystemUser> existsSystemUserData = systemUserRepo.findByEmail(existingUser.getEmail());
            if (existsSystemUserData.isEmpty()) {
                keycloak.realm(realm).users().delete(existingUser.getId());
            } else {
                throw new DuplicateEntryException("user already exists");
            }
        } else {
            Optional<SystemUser> byEmail = systemUserRepo.findByEmail(dto.getEmail());
            if (byEmail.isPresent()) {
                systemUserRepo.deleteById(byEmail.get().getUserId());
            }
        }

        UserRepresentation userRep = mapUserRep(dto);
        Response response = keycloak.realm(realm).users().create(userRep);
        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
            RoleRepresentation roleRepresentation = keycloak.realm(realm).roles().get("user").toRepresentation();
            userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
            keycloak.realm(realm).users().get(userId).roles().realmLevel().add(Arrays.asList(roleRepresentation));

            SystemUser systemUser = SystemUser.builder()
                    .userId(userId)
                    .email(dto.getEmail())
                    .status(true)
                    .fullName(dto.getFirstName() + " " + dto.getLastName())
                    .isEnabled(true)
                    .isAccountNonExpired(true)
                    .isAccountNonLocked(true)
                    .isCredentialsNonExpired(true)
                    .build();
            systemUserRepo.save(systemUser);

        }


    }

    private UserRepresentation mapUserRep(RequestSystemUserDto dto) {
        UserRepresentation userRep = new UserRepresentation();
        userRep.setUsername(dto.getEmail());
        userRep.setFirstName(dto.getFirstName());
        userRep.setLastName(dto.getLastName());
        userRep.setEmail(dto.getEmail());
        userRep.setEmailVerified(true);
        userRep.setEnabled(true);
        ArrayList<CredentialRepresentation> creds = new ArrayList<>();
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setValue(dto.getPassword());
        creds.add(credentialRepresentation);
        userRep.setCredentials(creds);
        return userRep;
    }

    @Override
    public Object login(String email, String password) {
        Optional<SystemUser> selectedUserObj = systemUserRepo.findByEmail(email);
        if (selectedUserObj.isEmpty()) {
            throw new EntryNotFoundException("User not found");
        }
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("client_id", clientId);
        requestBody.add("grant_type", OAuth2Constants.PASSWORD);
        requestBody.add("username", email);
        requestBody.add("client_secret", secret);
        requestBody.add("password", password);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = restTemplate.postForEntity(keyCloakApiUrl, requestBody, Object.class);
        return response.getBody();
    }
}
