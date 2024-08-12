package com.devops.user_service_api_1.service.impl;

import com.devops.user_service_api_1.dto.request.RequestSystemUserDto;
import com.devops.user_service_api_1.entity.SystemUser;
import com.devops.user_service_api_1.exception.DuplicateEntryException;
import com.devops.user_service_api_1.repo.SystemUserRepo;
import com.devops.user_service_api_1.service.SystemUserService;
import com.devops.user_service_api_1.util.KeycloakSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
        Keycloak keycloak=null;

        UserRepresentation existingUser=null;
        keycloak= keycloakSecurityUtil.getKeycloakInstance();

        existingUser = keycloak.realm(realm).users().search(dto.getEmail()).stream().findFirst().orElse(null);

        if(existingUser!=null){
            Optional<SystemUser> existsSystemUserData = systemUserRepo.findByEmail(existingUser.getEmail());
            if(existsSystemUserData.isEmpty()){
                        keycloak.realm(realm).users().delete(existingUser.getId());
            }else{
                throw new DuplicateEntryException("user already exists");
            }
        }else{
            Optional<SystemUser> byEmail = systemUserRepo.findByEmail(dto.getEmail());
            if(byEmail.isPresent()){
                systemUserRepo.deleteById(byEmail.get().getUserId());
            }
        }
        
        UserRepresentation userRep = mapUserRep(dto);


    }

    private UserRepresentation mapUserRep(RequestSystemUserDto dto) {
    }

    @Override
    public void login(String email, String password) {

    }
}
