package com.polarbookshop.orderservice.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Objects;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)){
            return Optional.of("anonymous");
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof Jwt) {
            return Optional.ofNullable(((Jwt) principal).getClaimAsString("preferred_username"));
        }else {
            return Optional.of(authentication.getName());
        }
    }
}
