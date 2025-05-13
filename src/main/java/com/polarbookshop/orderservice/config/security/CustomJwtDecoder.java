package com.polarbookshop.orderservice.config.security;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

public class CustomJwtDecoder implements JwtDecoder {

    private final JwtDecoder jwtDecoder;

    public CustomJwtDecoder(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        var errorMessage = "No decoders available for this token";
        try {
            return jwtDecoder.decode(token);
        } catch (JwtException exception) {
            errorMessage = exception.getMessage();
        }
        throw new JwtException(
                "Exception CustomJwtDecoder.decode : Unable to decode token. ErrorMessage: " + errorMessage);
    }
}
