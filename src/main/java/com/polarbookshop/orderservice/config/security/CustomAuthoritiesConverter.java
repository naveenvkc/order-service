package com.polarbookshop.orderservice.config.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        List<String> roles = source.getClaimAsStringList("roles");
        //List<String> scopes = source.getClaimAsStringList("scope");
        return roles.stream().map(role ->
            new SimpleGrantedAuthority("ROLE_" + role)
        ).collect(Collectors.toList());

    }
}
