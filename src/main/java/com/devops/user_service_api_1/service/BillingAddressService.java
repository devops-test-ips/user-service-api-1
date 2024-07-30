package com.devops.user_service_api_1.service;

import com.devops.user_service_api_1.dto.request.RequestBillingAddressDto;

public interface BillingAddressService {
    public void manage(RequestBillingAddressDto dto, String token);
}
