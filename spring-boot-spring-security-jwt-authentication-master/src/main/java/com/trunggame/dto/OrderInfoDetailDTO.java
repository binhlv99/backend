package com.trunggame.dto;

import com.trunggame.models.*;
import com.trunggame.models.Package;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoDetailDTO {
    private Long id;
    private Long gameId;
    private Integer quantity;
    private BigDecimal amount;
    private BigDecimal price;
    private Long packageId;
    private Order order;
    private Product product;
    private Package aPackage;
    private Categories categories;
    private String server;
    private String loginType;
    private String password;
    private String characterName;
    private String account;
    private String loginCode;
    private String status;
    private String description;
    private String previewUrl;
}
