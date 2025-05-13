package com.polarbookshop.orderservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.polarbookshop.orderservice.common.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("isbn")
    private String isbn;

    @JsonProperty("title")
    private String title;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("status")
    private OrderStatus status;

    @JsonProperty("created_date")
    private Instant createdDate;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("modified_by")
    private String modifiedBy;

    public static OrderResponse buildExample() {
        OrderResponse responseObject = new OrderResponse();
        responseObject.setId(1L);
        responseObject.setIsbn("1231231231");
        responseObject.setPrice(new BigDecimal("10.00"));
        responseObject.setQuantity(2);
        responseObject.setStatus(OrderStatus.ACCEPTED);
        responseObject.setCreatedDate(Instant.now());
        responseObject.setCreatedBy("testuser");
        responseObject.setModifiedBy("testuser");
        return responseObject;
    }
}
