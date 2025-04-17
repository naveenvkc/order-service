package com.polarbookshop.orderservice.exception;

import com.polarbookshop.orderservice.model.EmptyDataResponse;
import com.polarbookshop.orderservice.model.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@ControllerAdvice
public class OrderExceptionController {
    private static final Logger LOG = LoggerFactory.getLogger(OrderExceptionController.class);

    private static final String X_CONTENT_TYPE_OPTIONS = "X-Content-Type-Options";
    private static final String X_FRAME_OPTIONS = "X-Frame-Options";
    private static final String STRICT_TRANSPORT_SECURITY = "Strict-Transport-Security";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<EmptyDataResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex
    ) {
        LOG.error("Exception::MethodArgumentNotValidException");
        List<Notification> notifications = new ArrayList<>();
        ex.getFieldErrors()
                .stream()
                .filter(Objects::nonNull)
                .filter(error -> Objects.nonNull(error.getDefaultMessage()))
                .forEach(error -> {
                    Notification notification = new Notification();
                    notification.setSeverity("ERROR");
                    notification.setDescription(error.getDefaultMessage());
                    notification.setMessage(error.getDefaultMessage());
                    notification.setFieldName(error.getField());
                    notification.setUuid(UUID.randomUUID().toString());
                    notification.setNotificationDt(OffsetDateTime.now());

                    notifications.add(notification);
                });
        EmptyDataResponse error = new EmptyDataResponse();
        error.setNotifications(notifications);
        return new ResponseEntity<>(error, getResponseHeaders(), HttpStatus.BAD_REQUEST);
    }

    private HttpHeaders getResponseHeaders() {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(STRICT_TRANSPORT_SECURITY, "max-age=3153600; includeSubDomains");
        headers.add(X_FRAME_OPTIONS, "DENY");
        headers.add(X_CONTENT_TYPE_OPTIONS, "nosniff");

        return headers;
    }
}
