package com.trunggame.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "`order`")
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "code")
    private String code;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "status")
    private String status = "1";
    // 1 - cho xu ly, 2 - dang xu ly, 3 - thanh cong , 4 - Huỷ
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();


//    them colum

    @Column(name = "promotion_id")
    private Long promotionId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "order_code")
    private Long orderCode;

    @Column(name = "delivery_time")
    private String deliveryTime;

    @Column(name = "recipient_name")
    private String recipientName;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "ship")
    private String ship;

    @Column(name = "recipient_email")
    private String recipientEmail;

    @Column(name = "recipient_phone")
    private String recipientPhone;

    @Column(name = "address")
    private String address;

    @Column(name = "note")
    private String note;

    @Column(name = "payment_method")
    private LocalDateTime paymentMethod;

    @Column(name = "paid_amount")
    private String paidAmount;

    @Column(name = "payment_date")
    private String paymentDate;
}

