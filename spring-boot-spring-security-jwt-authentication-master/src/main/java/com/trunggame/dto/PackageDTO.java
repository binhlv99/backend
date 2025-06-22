package com.trunggame.dto;


import com.trunggame.models.CountryGroup;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Builder
@Getter
@Setter
public class PackageDTO {

    private Long id;

    private String name;

    private double price;

    private String unit;

    private double rating;

    private String serverGroup;

    private List<CountryGroup> server;

    private String listServer;

    private List<CountryGroup> removeServer;

    private String attribute;

    private int warehouseQuantity;

    private int tradeCount;

    private String descriptionVi;

    private String descriptionEn;

    private int deliveryTime;

    private Long gameId;

    private String status;

    private String imageId;

    private String previewUrl;

    private String topSale;

}
