package com.devops.user_service_api_1.service;

import com.devops.user_service_api_1.dto.request.RequestSystemUserDto;

public interface SystemUserService {
    public void signup(RequestSystemUserDto dto);
    public void login(String email,String password);
}
