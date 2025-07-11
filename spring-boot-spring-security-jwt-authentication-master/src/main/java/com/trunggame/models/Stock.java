package com.trunggame.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`stock`")
public class Stock {
    @Id
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Integer product_Id;

    @Column(name = "quantity",nullable = false)
    private Integer quantity;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

//    them colum

    @Column(name = "quantity_remaining")
    private Integer quantityRemaining;


    public enum Status {
        ACTIVE,
        INACTIVE
    }

}

