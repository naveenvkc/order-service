package com.polarbookshop.orderservice.entity;

import com.polarbookshop.orderservice.common.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "orders", schema = "polar_bookshop")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@EntityListeners(AuditingEntityListener.class)
public class OrderEntity implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String bookIsbn;

    private String bookName;

    private BigDecimal bookPrice;

    private Integer quantity;

    private String status;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    @Version
    private int version;

    public static OrderEntity of(String bookIsbn, String bookName, BigDecimal bookPrice, Integer quantity, String status) {
        return new OrderEntity(null, bookIsbn, bookName, bookPrice, quantity, status, null, null,null, null, 0);
    }
}
