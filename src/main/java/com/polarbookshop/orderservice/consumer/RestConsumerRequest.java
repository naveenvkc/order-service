package com.polarbookshop.orderservice.consumer;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
@Builder
public class RestConsumerRequest<I>{

    private I request;

    private Map<String, Object> headerParams;
    private Map<String, Object> queryParams;
    private Map<String, Object> pathParams;
    private Map<String, Object> formData;
}
