package com.devops.user_service_api_1.api;

import com.devops.user_service_api_1.dto.request.RequestSystemUserDto;
import com.devops.user_service_api_1.service.SystemUserService;
import com.devops.user_service_api_1.util.StandardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("user-service/api/v1/users")
@RestController
@RequiredArgsConstructor
public class SystemUserController {

    private SystemUserService systemUserService;

    @PostMapping("/signup")
    public ResponseEntity<StandardResponse> signup(
            @RequestBody RequestSystemUserDto dto
    ) {
        systemUserService.signup(dto);
        return new ResponseEntity<>(
                new StandardResponse(201, "User registered..", null),
                HttpStatus.CREATED
        );
    }
}
