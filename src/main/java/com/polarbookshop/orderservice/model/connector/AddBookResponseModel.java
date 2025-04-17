package com.polarbookshop.orderservice.model.connector;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddBookResponseModel {

    @JsonProperty("data")
    private BookResponseModel data;

    @JsonProperty("notifications")
    private List<NotificationResponse> notifications = null;

}
