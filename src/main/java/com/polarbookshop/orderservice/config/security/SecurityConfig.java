package com.polarbookshop.orderservice.config.security;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final OAuth2ResourceServerProperties oAuth2ResourceServerProperties;

    public SecurityConfig(OAuth2ResourceServerProperties oAuth2ResourceServerProperties) {
        this.oAuth2ResourceServerProperties = oAuth2ResourceServerProperties;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        return security
                .authorizeHttpRequests(customizer -> customizer.requestMatchers(
                                AntPathRequestMatcher.antMatcher("/swagger-ui/index.html"),
                                AntPathRequestMatcher.antMatcher("/swagger-ui/**"),
                                AntPathRequestMatcher.antMatcher("/v3/api-docs/**"),
                                AntPathRequestMatcher.antMatcher("/actuator/**")
                ).permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(customizer ->
                        customizer.jwt(jwt -> jwt.decoder(jwtDecoderEnv())
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .build();

    }

    @Bean("jwtDecoderEnv")
    public JwtDecoder jwtDecoderEnv() {
        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromOidcIssuerLocation(
                oAuth2ResourceServerProperties.getJwt().getIssuerUri());
        return new CustomJwtDecoder(jwtDecoder);
    }

    @Bean("jwtAuthenticationConverterEnv")
    public Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new CustomAuthoritiesConverter());
        return converter;
    }
}
