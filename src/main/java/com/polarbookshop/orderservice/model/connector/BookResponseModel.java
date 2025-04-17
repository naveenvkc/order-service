package com.polarbookshop.orderservice.model.connector;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponseModel {

    @JsonProperty("isbn")
    private String isbn;

    @JsonProperty("title")
    private String title;

    @JsonProperty("author")
    private String author;

    @JsonProperty("price")
    private BigDecimal price;

}
