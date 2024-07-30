package com.devops.user_service_api_1.repo;

import com.devops.user_service_api_1.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemUserRepo
        extends JpaRepository<SystemUser,String> {
}
