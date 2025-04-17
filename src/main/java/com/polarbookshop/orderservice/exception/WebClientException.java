package com.polarbookshop.orderservice.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class WebClientException extends Exception{

    private String errorReference;
    private HttpStatus errorCode;

    public WebClientException(String message, String errorReference) {
        super(message);
        this.errorReference = errorReference;
    }

    public WebClientException(String message, HttpStatus code, String errorReference) {
        super(message);
        this.errorCode = code;
        this.errorReference = errorReference;
    }

}
