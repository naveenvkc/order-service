package com.polarbookshop.orderservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Notification {

    @JsonProperty("category")
    private String category;

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("description")
    private String description;

    @JsonProperty("action")
    private String action;

    @JsonProperty("metadata")
    private Map<String, Object> metadata = null;

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("notification_dt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime notificationDt;

    @JsonProperty("severity")
    private String severity;

    @JsonProperty("field_name")
    private String fieldName;

    public static Notification buildExample() {
        Notification responseObject = new Notification();
        responseObject.setCategory(" ");
        responseObject.setCode(" ");
        responseObject.setMessage(" ");
        responseObject.setDescription(" ");
        responseObject.setAction(" ");
        responseObject.setMetadata(new HashMap<>());
        responseObject.setUuid(" ");
        responseObject.setNotificationDt(OffsetDateTime.now(java.time.ZoneOffset.UTC));
        responseObject.setSeverity(" ");
        responseObject.setFieldName(" ");
        return responseObject;
    }
}
