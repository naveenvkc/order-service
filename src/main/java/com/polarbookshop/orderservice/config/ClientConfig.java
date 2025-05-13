package com.polarbookshop.orderservice.config;

import com.polarbookshop.orderservice.operation.GetAllOrdersOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {
    private static final Logger LOG = LoggerFactory.getLogger(ClientConfig.class);
    @Bean
    WebClient webClient(ClientProperties clientProperties, WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(clientProperties.getCatalogServiceUri().toString())
                .build();
    }
}
