package com.trunggame.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_type")
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String name;

    @Column(name = "description",columnDefinition = "VARCHAR(5000) CHARACTER SET utf8")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ACTIVE;

    @Column(name = "category_code")
    private String categoryCode;

    @Column(name = "image_id")
    private String imageId;


    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "product_id")
    private Long gameId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    public enum Status {
        ACTIVE,
        INACTIVE
    }

//    them colum
@Column(name = "style_name")
private String styleName;

}