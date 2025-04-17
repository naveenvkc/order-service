package com.polarbookshop.orderservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polarbookshop.orderservice.exception.WebClientException;
import com.polarbookshop.orderservice.model.connector.AddBookResponseModel;
import com.polarbookshop.orderservice.model.connector.NotificationResponse;
import com.polarbookshop.orderservice.operation.GetAllOrdersOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class WebClientUtil {
    private static final Logger LOG = LoggerFactory.getLogger(WebClientUtil.class);
    private static final ObjectMapper OBJ_MAPPER = new ObjectMapper();

    private WebClientUtil() {
        // Empty constructor
    }

    public static Function<ClientResponse, Mono<? extends Throwable>> logWebClientError(
            String location, String errorReference, String exceptionMessage, Object requestBody
    ) {
        String reqBody = "{ NA }";
        if (Objects.nonNull(requestBody)) {
            try {
                reqBody = OBJ_MAPPER.writeValueAsString(requestBody);
            } catch (JsonProcessingException ignore) {
                reqBody = "{ (could not write object as string...) }";
            }
        }

        final String finalReqBody = reqBody;
        return response -> response.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
        }).onErrorResume(Exception.class, e -> {
            String statusCode = String.valueOf(response.statusCode().value());

            LOG.error(
                    "Error WebClient Response in {} - Error Reference {} Status Code {} Response Body (Parameterized Type Reference Failed) {} Request Body {}",
                    location,
                    errorReference,
                    statusCode,
                    e.getMessage(),
                    finalReqBody
            );

            final String expMessage = statusCode.startsWith("5") ? "Service unavailable. Error reference " + errorReference : exceptionMessage;
            return Mono.error(new WebClientException(expMessage, errorReference));
        }).flatMap(error -> {
            String statusCode = String.valueOf(response.statusCode().value());

            LOG.error(
                    "Error WebClient Response in {} - Error Reference {} Status Code {} Response Body {} Request Body {}",
                    location,
                    errorReference,
                    response.statusCode().value(),
                    error.toString(),
                    finalReqBody
            );

            final String expMessage = statusCode.startsWith("5") ? "Service unavailable. Error reference " + errorReference : exceptionMessage;
            return Mono.error(new WebClientException(expMessage, errorReference));
        });
    }

    public static Function<ClientResponse, Mono<? extends Object>> webClientError() {
        return response -> response.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
        }).onErrorResume(Exception.class, e -> {
            String statusCode = String.valueOf(response.statusCode().value());


            //final String expMessage = statusCode.startsWith("5") ? "Service unavailable. Error reference " + errorReference : exceptionMessage;
            return Mono.error(new WebClientException("", ""));
        }).flatMap(error -> {
            String statusCode = String.valueOf(response.statusCode().value());
            if(statusCode.equals("404"))
                return Mono.just(build4xxNotification());
            return Mono.empty();
        });
    }

    private static Mono<AddBookResponseModel> build4xxNotification(){
        return Mono.just(AddBookResponseModel.builder().notifications(
                List.of(NotificationResponse.builder().code("4xx").build())).build());
    }
}
