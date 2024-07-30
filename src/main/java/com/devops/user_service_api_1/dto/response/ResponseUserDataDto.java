package com.devops.user_service_api_1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseUserDataDto {
    private String email;
    private String firstname;
    private String lastName;
    private ResponseBillingAddress billingAddress;
}
