package com.trunggame.models;

import com.trunggame.enums.ECategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`categories`")
public class Categories {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false,columnDefinition = "VARCHAR(5000) CHARACTER SET utf8")
    private String name;


    @Column(name = "description",columnDefinition = "VARCHAR(5000) CHARACTER SET utf8")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ECategoryStatus status;

    @Column(name = "parent_id")
    private Long parentId;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

//    them colum
    @Column(name = "category_code")
    private String categoryCode;

}

