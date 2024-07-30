package com.devops.user_service_api_1.repo;

import com.devops.user_service_api_1.entity.BillingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingAddressRepo
        extends JpaRepository<BillingAddress,String> {
}
