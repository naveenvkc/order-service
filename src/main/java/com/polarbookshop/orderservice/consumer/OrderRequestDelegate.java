package com.polarbookshop.orderservice.consumer;

import com.polarbookshop.orderservice.model.OrderRequest;
import com.polarbookshop.orderservice.model.OrderResponseModel;
import com.polarbookshop.orderservice.model.SubmitOrderResponseModel;
import com.polarbookshop.orderservice.operation.GetAllOrdersOperation;
import com.polarbookshop.orderservice.operation.SubmitOrderOperation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderRequestDelegate {
    private static final String X_CONTENT_TYPE_OPTIONS = "X-Content-Type-Options";
    private static final String X_FRAME_OPTIONS = "X-Frame-Options";
    private static final String STRICT_TRANSPORT_SECURITY = "Strict-Transport-Security";

    private final GetAllOrdersOperation getAllOrdersOperation;
    private final SubmitOrderOperation submitOrderOperation;

    public OrderRequestDelegate(
            @Qualifier("getAllOrdersOperation") GetAllOrdersOperation getAllOrdersOperation,
            @Qualifier("submitOrderOperation") SubmitOrderOperation submitOrderOperation) {
        this.getAllOrdersOperation = getAllOrdersOperation;
        this.submitOrderOperation = submitOrderOperation;
    }

    public ResponseEntity<OrderResponseModel> getAllOrders(RestConsumerRequest<Void> consumerRequest) {
        return ResponseEntity.status(HttpStatus.OK).headers(getResponseHeaders())
                .body(getAllOrdersOperation.handle(consumerRequest));
    }

    public ResponseEntity<SubmitOrderResponseModel> submitOrder(RestConsumerRequest<OrderRequest> consumerRequest) {
        return ResponseEntity.status(HttpStatus.OK).headers(getResponseHeaders())
                .body(submitOrderOperation.handle(consumerRequest));
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
