package com.polarbookshop.orderservice.clients;

import com.polarbookshop.orderservice.common.Constants;
import com.polarbookshop.orderservice.exception.WebClientException;
import com.polarbookshop.orderservice.model.connector.AddBookResponseModel;
import com.polarbookshop.orderservice.model.connector.NotificationResponse;
import com.polarbookshop.orderservice.util.WebClientUtil;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Component
public class BookClient {
    private static final String BOOKS_ROOT_API = "/api/v1/books/";
    private final WebClient webClient;

    public BookClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<AddBookResponseModel> getBookByIsbn(String isbn) {
        String errorReference = UUID.randomUUID().toString();
        return webClient
                .get()
                .uri(BOOKS_ROOT_API + isbn)
                .headers(h -> {
                    h.set(Constants.X_B3_TRACEID, "1234");
                    h.set(Constants.X_B3_SPANID, "1234");
                })
                .retrieve()
//                .onStatus(HttpStatusCode::isError, WebClientUtil.logWebClientError(
//                        "AutoDirectConnectorUtil.getUserDealerInfo",
//                        errorReference,
//                        "Could not retrieve the dealer by location id. Error reference " + errorReference,
//                        null
//                ))
                //.onStatus(HttpStatusCode::isError,WebClientUtil.webClientError())
                .bodyToMono(AddBookResponseModel.class)
                .timeout(Duration.ofSeconds(3), Mono.empty())
                //.onErrorResume(WebClientResponseException.NotFound.class, exception -> build4xxNotification())
                .onErrorResume(WebClientResponseException.NotFound.class, exception -> Mono.empty())
                .retryWhen(Retry.backoff(3, Duration.ofMillis(100)).jitter(0.75))
                //.onErrorResume(Exception.class, exception -> Mono.error(new WebClientException("Service Unavailable", errorReference)))
                .onErrorMap(
                        Exception.class,
                        ex -> new WebClientException(ex.getMessage(),
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                errorReference
                        )
                );
    }

    private static Mono<AddBookResponseModel> build4xxNotification(){
        return Mono.just(AddBookResponseModel.builder().notifications(
                List.of(NotificationResponse.builder().code("4xx").build())).build());
    }
}
