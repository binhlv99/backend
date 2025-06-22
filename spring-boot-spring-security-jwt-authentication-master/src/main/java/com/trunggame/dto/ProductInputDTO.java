package com.trunggame.dto;


import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.trunggame.models.Product;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.util.Set;

@Data
@Builder
@Getter
@Setter

public class ProductInputDTO {

    private Long id;

    private String name;

    private String description;

    @JsonPropertyDescription("Các loại game khác nhau sẽ yêu cầu require các trường khác nhau")
    private String type;

    @JsonPropertyDescription("Độ ưu tiên của game: hot, new, sale off, gift, normal" +
            "Lưu tương ứng xuống databse là 1-hot, 2-top, 3- new, 4 - gift, 5 - best seller ... tuỳ các thánh định nghĩa")
    @Builder.Default
    private String gamePriority = "normal";

    private Long categoryId;

    private String imageId;

    private String thumbnail;

    private String contentVi;

    private String contentEn;

    private String descriptionEn;

    private Set<String> tags;

    private String youtubeLink;

    private String marketType;

    private String brandName;

    private Product.Status status;

}
