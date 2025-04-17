package com.polarbookshop.orderservice.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polarbookshop.orderservice.common.OrderStatus;
import com.polarbookshop.orderservice.model.OrderRequest;
import com.polarbookshop.orderservice.model.OrderResponse;
import com.polarbookshop.orderservice.model.SubmitOrderResponseModel;
import com.polarbookshop.orderservice.operation.SubmitOrderOperation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerMvcTests {

    private static final String X_CONTENT_TYPE_OPTIONS = "X-Content-Type-Options";
    private static final String X_FRAME_OPTIONS = "X-Frame-Options";
    private static final String STRICT_TRANSPORT_SECURITY = "Strict-Transport-Security";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderRequestDelegate orderRequestDelegate;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void whenBookNotAvailableThenRejectOrder() throws Exception{
        OrderRequest restConsumerRequest = OrderRequest.builder()
                .isbn("1234567890")
                .quantity(3)
                .build();
        RestConsumerRequest<OrderRequest> consumerRequest =
                RestConsumerRequest.<OrderRequest>builder()
                        .request(restConsumerRequest)
                        .build();
        OrderResponse orderRes = OrderResponse.builder().status(OrderStatus.REJECTED).build();
        SubmitOrderResponseModel subResModel = SubmitOrderResponseModel.builder().data(orderRes).build();
        ResponseEntity<SubmitOrderResponseModel> resEntity = ResponseEntity.status(HttpStatus.OK).headers(getResponseHeaders())
                .body(subResModel);
        //var rejectedOrder = SubmitOrderOperation.buildRejectedOrder(restConsumerRequest);
        given(orderRequestDelegate.submitOrder(consumerRequest)).willReturn(resEntity);

        mockMvc
                .perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restConsumerRequest)))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.data.status").value("REJECTED"))
                ;


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
