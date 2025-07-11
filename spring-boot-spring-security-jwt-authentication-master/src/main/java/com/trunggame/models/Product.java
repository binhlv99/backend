package com.trunggame.models;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String name;

    @Column(name = "description",length = 10000, columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")

    private String description;

    @Column(name = "description_en",length = 10000, columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String descriptionEn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ACTIVE;

    @Column(nullable = false)
    private String type;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "image_id")
    private String imageId;


    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "youtubeLink")
    private String youtubeLink;

    @Column(name = "content_vi",length = 10000, columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String contentVi;

    @Column(name = "content_en",length = 10000, columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String contentEn;

    @Column(name = "marketType")
    private String marketType;

    @Column(name = "productPriority")
    private String gamePriority;

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

    @Column(name = "color_id")
    private Long colorId;

    @Column(name = "style_id")
    private Long styleId;

    @Column(name = "size_id")
    private Long sizeId;

    @Column(name = "shoe_type_id")
    private Long shoeTypeId;

    @Column(name = "toe_style_id")
    private Long toeStyleId;

    @Column(name = "collar_type_id")
    private Long colorTypeId;

    @Column(name = "sole_id")
    private Long moleId;

    @Column(name = "material_id")
    private Long materialId;

    @Column(name = "brand_id")
    private Long brandId;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "price")
    private Float price;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "created_by")
    private Long createdBy;


    public enum Status {
        ACTIVE,
        INACTIVE
    }
}