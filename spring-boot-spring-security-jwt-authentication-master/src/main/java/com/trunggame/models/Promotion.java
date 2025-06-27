package com.trunggame.models;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "promotion")
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NonNull
//    @Column(nullable = false)
//    private String name;

    @Column(name = "description",length = 10000, columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")

    private String description;

    @Column(name = "description_en",length = 10000, columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String descriptionEn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ACTIVE;

//    @Column(nullable = false)
//    private String type;

    @Column(name = "code")
    private Long code;

    @Column(name = "discount_voucher")
    private String discountVoucher;


    @Column(name = "voucher_type")
    private String voucherType;

    @Column(name = "discount_value")
    private String discountValue;

    @Column(name = "content_vi",length = 10000, columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String contentVi;

    @Column(name = "content_en",length = 10000, columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String contentEn;

    @Column(name = "minimum_order_value")
    private String minimumOrderValue;

    @Column(name = "maximum_discount_value")
    private String maximumDiscountValue;

    @Column(name = "brandName",length = 10000, columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String brandName;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Transient
    private String previewUrl;
//    thêm colum

    @Column(name = "discount_type")
    private String discountType;

    @Column(name = "discount_value_type")
    private String discountValueType;

    @Column(name = "expiration_date")
    private String expirationDate;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "promotion_name")
    private String promotionName;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "created_by")
    private Long createdBy;


    public enum Status {
        ACTIVE,
        INACTIVE
    }
}