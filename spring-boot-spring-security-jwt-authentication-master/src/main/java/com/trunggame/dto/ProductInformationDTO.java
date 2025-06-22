package com.trunggame.dto;

import com.trunggame.models.Color;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@Getter
@Setter
public class ProductInformationDTO {
    private Long id;
    private String name;
    private String description;
    private String status;
    private String hot;
    private String type;
    private Long categoryId;
    private String categoryName;
    private String imageUrl;
    private String thumbnailUrl;
    private BigDecimal price;
    private BigDecimal promotionPrice;
    private BigDecimal promotionPercent;
    private String brandName;
    private String marketType;
    private String youtubeLink;
    private String contentEn;
    private String contentVi;
    private String descriptionEn;
    private String gamePriority;
    private List<String> tags;
    private List<PackageDTO> gamePackages;
    private List<Color> server;

}
