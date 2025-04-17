package com.polarbookshop.orderservice.operation;

public interface IOperation<I,O> {
    O handle(I consumerRequest);
}
