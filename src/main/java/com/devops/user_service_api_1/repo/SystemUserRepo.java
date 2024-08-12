package com.devops.user_service_api_1.repo;

import com.devops.user_service_api_1.entity.SystemUser;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SystemUserRepo
        extends JpaRepository<SystemUser,String> {

     Optional<SystemUser> findByEmail(String email);
}
