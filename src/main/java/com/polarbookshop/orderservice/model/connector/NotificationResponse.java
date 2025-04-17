package com.polarbookshop.orderservice.model.connector;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Generated
public class NotificationResponse {

    @JsonProperty("code")
    private String code;

    @JsonProperty("source")
    private String source;

    @JsonProperty("severity")
    private String severity;

    @JsonProperty("message")
    private String message;

    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("notification_dt")
    private OffsetDateTime notificationDt;

}
