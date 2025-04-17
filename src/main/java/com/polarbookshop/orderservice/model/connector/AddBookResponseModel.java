package com.polarbookshop.orderservice.model.connector;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class AddBookResponseModel {

    @JsonProperty("data")
    private final BookResponseModel data;

    @JsonProperty("notifications")
    @Builder.Default
    private List<NotificationResponse> notifications = null;

}
