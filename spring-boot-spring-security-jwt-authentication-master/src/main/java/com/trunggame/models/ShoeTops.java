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

//    them colum

    @Column(name = "shoe_tops_name")
    private String shoeTopsName;

    @Column(name = "description",columnDefinition = "VARCHAR(5000) CHARACTER SET utf8")
    private String description;
}
