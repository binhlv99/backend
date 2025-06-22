package com.trunggame.repository.impl;

import com.trunggame.dto.ProductInformationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryCustom {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public List<ProductInformationDTO> getAllInformation() {

        String sql =
                "SELECT distinct s.id," +
                        "       s.name," +
                        "       s.description," +
                        "       s.status," +
                        "       s.type AS gameType," +
                        "       s.company_name AS companyName," +
                        "       s.thumbnail AS thumbnail," +
                        "       s.market_type AS marketType," +
                        "       s.youtube_link AS youtubeLink," +
                        "       s.content_en AS contentEn," +
                        "       f.preview_url AS imageUrl," +
                        "       p.price," +
                        "       p.promotion_price AS promotionPrice," +
                        "       p.promotion_percent AS promotionPercent," +
                        "       gc.id AS categoryId," +
                        "       gc.name AS categoryName," +
                        "       s.content_vi AS contentVi," +
                        "       s.description_en AS descriptionEn," +
                        "       s.game_priority AS gamePriority" +
                        "       FROM game AS s" +
                        "           LEFT JOIN file AS f ON f.uniq_id = s.thumbnail" +
                        "           JOIN categories AS gc ON gc.id = s.category_id" +
                        "           LEFT JOIN price AS p ON p.game_id = s.id" +
                        "       where gc.status = 'ACTIVE' ";

        System.out.println(sql);

        return jdbcTemplate.query(sql, (rs, rowNum) -> ProductInformationDTO.
                builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .status(rs.getString("status"))
                .type(rs.getString("gameType"))
                .imageUrl(rs.getString("imageUrl"))
                .price(rs.getBigDecimal("price"))
                .promotionPercent(rs.getBigDecimal("promotionPercent"))
                .promotionPrice(rs.getBigDecimal("promotionPrice"))
                .categoryId(rs.getLong("categoryId"))
                .marketType(rs.getString("marketType"))
                .categoryName(rs.getString("categoryName"))
                .companyName(rs.getString("companyName"))
                .youtubeLink(rs.getString("youtubeLink"))
                .thumbnailUrl(rs.getString("thumbnail"))
                .contentEn(rs.getString("contentEn"))
                .contentVi(rs.getString("contentVi"))
                .descriptionEn(rs.getString("descriptionEn"))
                .gamePriority(rs.getString("gamePriority"))
                .build());
    }
    public List<ProductInformationDTO> getActiveGame() {

        String sql =
                "SELECT distinct s.id," +
                        "       s.name," +
                        "       s.description," +
                        "       s.status," +
                        "       s.type AS gameType," +
                        "       s.company_name AS companyName," +
                        "       s.thumbnail AS thumbnail," +
                        "       s.market_type AS marketType," +
                        "       s.youtube_link AS youtubeLink," +
                        "       s.content_en AS contentEn," +
                        "       f.preview_url AS imageUrl," +
                        "       p.price," +
                        "       p.promotion_price AS promotionPrice," +
                        "       p.promotion_percent AS promotionPercent," +
                        "       gc.id AS categoryId," +
                        "       gc.name AS categoryName," +
                        "       s.content_vi AS contentVi," +
                        "       s.description_en AS descriptionEn," +
                        "       s.game_priority AS gamePriority" +
                        "       FROM game AS s" +
                        "           LEFT JOIN file AS f ON f.uniq_id = s.thumbnail" +
                        "           JOIN categories AS gc ON gc.id = s.category_id" +
                        "           LEFT JOIN price AS p ON p.game_id = s.id" +
                        "       where s.status = 'ACTIVE' ";

        System.out.println(sql);

        return jdbcTemplate.query(sql, (rs, rowNum) -> ProductInformationDTO.
                builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .status(rs.getString("status"))
                .type(rs.getString("gameType"))
                .imageUrl(rs.getString("imageUrl"))
                .price(rs.getBigDecimal("price"))
                .promotionPercent(rs.getBigDecimal("promotionPercent"))
                .promotionPrice(rs.getBigDecimal("promotionPrice"))
                .categoryId(rs.getLong("categoryId"))
                .marketType(rs.getString("marketType"))
                .categoryName(rs.getString("categoryName"))
                .companyName(rs.getString("companyName"))
                .youtubeLink(rs.getString("youtubeLink"))
                .thumbnailUrl(rs.getString("thumbnail"))
                .contentEn(rs.getString("contentEn"))
                .contentVi(rs.getString("contentVi"))
                .descriptionEn(rs.getString("descriptionEn"))
                .gamePriority(rs.getString("gamePriority"))
                .build());
    }

    public List<ProductInformationDTO> getNewGamge() {

        String sql =
                "SELECT distinct s.id," +
                        "       s.name," +
                        "       s.description," +
                        "       s.status," +
                        "       s.type AS gameType," +
                        "       s.company_name AS companyName," +
                        "       s.thumbnail AS thumbnail," +
                        "       s.market_type AS marketType," +
                        "       s.youtube_link AS youtubeLink," +
                        "       s.content_en AS contentEn," +
                        "       f.preview_url AS imageUrl," +
                        "       p.price," +
                        "       p.promotion_price AS promotionPrice," +
                        "       p.promotion_percent AS promotionPercent," +
                        "       gc.id AS categoryId," +
                        "       gc.name AS categoryName," +
                        "       s.content_vi AS contentVi," +
                        "       s.description_en AS descriptionEn," +
                        "       gc.created_at " +
                        "       FROM game AS s" +
                        "           LEFT JOIN file AS f ON f.uniq_id = s.thumbnail " +
                        "           JOIN categories AS gc ON gc.id = s.category_id" +
                        "           LEFT JOIN price AS p ON p.game_id = s.id" +
                       "        where s.status = 'ACTIVE' " +
                        "and s.game_priority ='3'";

        System.out.println(sql);

        return jdbcTemplate.query(sql, (rs, rowNum) -> ProductInformationDTO.
                builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .status(rs.getString("status"))
                .type(rs.getString("gameType"))
                .imageUrl(rs.getString("imageUrl"))
                .price(rs.getBigDecimal("price"))
                .promotionPercent(rs.getBigDecimal("promotionPercent"))
                .promotionPrice(rs.getBigDecimal("promotionPrice"))
                .categoryId(rs.getLong("categoryId"))
                .marketType(rs.getString("marketType"))
                .categoryName(rs.getString("categoryName"))
                .companyName(rs.getString("companyName"))
                .youtubeLink(rs.getString("youtubeLink"))
                .thumbnailUrl(rs.getString("thumbnail"))
                .contentEn(rs.getString("contentEn"))
                .contentVi(rs.getString("contentVi"))
                .descriptionEn(rs.getString("descriptionEn"))
                .build());
    }

}
