package com.polarbookshop.orderservice.config;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.net.URI;

@ConfigurationProperties(prefix = "polar")
@Getter
@Setter
@Validated
public class ClientProperties {

    @NotNull
    private URI catalogServiceUri;
}
