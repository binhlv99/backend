package com.trunggame.dto;

import com.trunggame.models.Post;
import lombok.*;

import java.util.List;


@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadDataDTO {

    private List<ProductInformationDTO> listGame;
    private List<BannerDTO> banners;
    private List<Post> posts;
    private List<ProductInformationDTO> newGames;
    private List<PackageDTO> topSale;
    private List<PackageDTO> newPackage;
    private List<PackageDTO> bestSale;
    private List<PackageDTO> packages;
}
