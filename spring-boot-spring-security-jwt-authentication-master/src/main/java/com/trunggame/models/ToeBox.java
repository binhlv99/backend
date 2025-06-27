package com.trunggame.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "toebox")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToeBox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",columnDefinition = "VARCHAR(5000) CHARACTER SET utf8")
    private String name;

    @Column(name = "description",columnDefinition = "VARCHAR(5000) CHARACTER SET utf8")
    private String description;
//them colum

    @Column(name = "toe_style_name")
    private String toeStyleName;

}
