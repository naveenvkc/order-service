package com.polarbookshop.orderservice.operation;

import com.polarbookshop.orderservice.clients.BookClient;
import com.polarbookshop.orderservice.common.OrderStatus;
import com.polarbookshop.orderservice.consumer.RestConsumerRequest;
import com.polarbookshop.orderservice.entity.OrderEntity;
import com.polarbookshop.orderservice.exception.OrderNotFoundException;
import com.polarbookshop.orderservice.mapper.OrderMapper;
import com.polarbookshop.orderservice.model.OrderRequest;
import com.polarbookshop.orderservice.model.OrderResponse;
import com.polarbookshop.orderservice.model.SubmitOrderResponseModel;
import com.polarbookshop.orderservice.model.connector.AddBookResponseModel;
import com.polarbookshop.orderservice.persistence.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Mono;

@Component
public class SubmitOrderOperation implements IOperation<RestConsumerRequest<OrderRequest>, SubmitOrderResponseModel>{
    private static final Logger LOG = LoggerFactory.getLogger(SubmitOrderOperation.class);

    private final OrderRepository orderRepository;
    private final BookClient bookClient;

    public SubmitOrderOperation(OrderRepository orderRepository,
                                BookClient bookClient) {
        this.orderRepository = orderRepository;
        this.bookClient = bookClient;
    }

    @Override
    public SubmitOrderResponseModel handle(RestConsumerRequest<OrderRequest> consumerRequest) {
        LOG.info("Entered SubmitOrderResponseModel.handle Operation");
        return prepareConsumerResponse(consumerRequest);
    }

    private SubmitOrderResponseModel prepareConsumerResponse(
            RestConsumerRequest<OrderRequest> restConsumerRequest) {
        LOG.info("Entered prepareConsumerResponse Operation");
        //OrderEntity orderEntity = orderRepository.save(buildRejectedOrder(consumerRequest));

        //get book info by calling catalog service
//        Mono<AddBookResponseModel> bookResponse = bookClient.getBookByIsbn(consumerRequest.getIsbn());
//        bookResponse.subscribe(book -> {
//            if (!ObjectUtils.isEmpty(book) && !ObjectUtils.isEmpty(book.getData())){
//                orderRepository.save(buildAcceptedOrder(book, orderEntity, consumerRequest.getQuantity()));
//            }
//        });
        OrderRequest consumerRequest = restConsumerRequest.getRequest();
        OrderEntity orderEntity;
        AddBookResponseModel bookResponse = bookClient.getBookByIsbn(consumerRequest.getIsbn()).block();
        if (!ObjectUtils.isEmpty(bookResponse) && !ObjectUtils.isEmpty(bookResponse.getData())){
            orderEntity =
                    orderRepository.save(buildAcceptedOrder(bookResponse, consumerRequest.getQuantity()));
        }else {
            if(!ObjectUtils.isEmpty(bookResponse) && !ObjectUtils.isEmpty(bookResponse.getNotifications()) && !bookResponse.getNotifications().isEmpty()){
                orderEntity = orderRepository.save(buildPendingOrder(consumerRequest));
            }else {
                orderEntity = orderRepository.save(buildRejectedOrder(consumerRequest));
            }

        }

        //get the latest order
        OrderResponse orderResponse =
                OrderMapper.mapEntityToResponse(orderEntity);
        return SubmitOrderResponseModel.builder()
                .data(orderResponse)
                .build();
    }

    private OrderEntity getOrderEntity(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public static OrderEntity buildAcceptedOrder(
            AddBookResponseModel bookResponse, int quantity){
        return OrderEntity.of(
                bookResponse.getData().getIsbn(),
                bookResponse.getData().getTitle() + " - " + bookResponse.getData().getAuthor(),
                bookResponse.getData().getPrice(), quantity, OrderStatus.ACCEPTED.getStatus());
    }

    public static OrderEntity buildRejectedOrder(OrderRequest request){
        return OrderEntity.of( request.getIsbn(), null, null, request.getQuantity(),
                OrderStatus.REJECTED.getStatus());
    }

    public static OrderEntity buildPendingOrder(OrderRequest request){
        return OrderEntity.of( request.getIsbn(), null, null, request.getQuantity(),
                OrderStatus.PENDING.getStatus());
    }
}
