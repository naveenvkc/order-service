package com.polarbookshop.orderservice.model;

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

    public static NotificationResponse buildExample() {
        NotificationResponse responseObject = new NotificationResponse();
        responseObject.setCode(" ");
        responseObject.setSource(" ");
        responseObject.setSeverity(" ");
        responseObject.setMessage(" ");
        responseObject.setUuid(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"));
        responseObject.setNotificationDt(OffsetDateTime.now(ZoneOffset.UTC));
        return responseObject;
    }
}
