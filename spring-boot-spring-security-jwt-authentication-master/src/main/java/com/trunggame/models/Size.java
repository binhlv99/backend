package com.trunggame.models;


import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name = "size")
@Builder
@AllArgsConstructor
public class Size {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",columnDefinition = "VARCHAR(5000) CHARACTER SET utf8")
    private String name;

    @Column(name = "description",columnDefinition = "VARCHAR(5000) CHARACTER SET utf8")
    private String description;

    public Size() {
    }

    public Size(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    them colum

    @Column(name = "size_name")
    private String sizeName;

}
