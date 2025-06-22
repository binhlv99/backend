package com.trunggame.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`shoe_tops`")
@Entity
public class ShoeTops {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "shoe_outsoles_id", nullable = false)
    private Long shoeOutsoleId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

}
