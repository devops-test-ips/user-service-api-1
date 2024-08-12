package com.devops.user_service_api_1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicateEntryException extends RuntimeException{
    public DuplicateEntryException(String message) {
        super(message);
    }
}
