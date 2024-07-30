package com.devops.user_service_api_1.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-service/api/v1/tests")
public class TestController {

    @GetMapping
    public String test(){
        return "User Service Running on port (8001)";
    }
}
