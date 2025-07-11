package com.trunggame.repository.impl;

import com.trunggame.dto.PackageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PackageRepositoryImpl {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<PackageDTO> getAllPackage() {

        String sql = "SELECT   gp.id ,gp.description_vi,gp.description_en ,gp.attribute ,gp.game_id ,gp.name ,gp.price ,gp.rating ,gp.warehouse_quantity ,gp.unit ,gp.trade_count, f.preview_url ,gp.status, gp.top_sale from package gp \n" +
                        "\tjoin file f on f.uniq_id  =gp.image_id ";

        System.out.println(sql);

        return jdbcTemplate.query(sql, (rs, rowNum) -> PackageDTO.
                builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .price(rs.getLong("price"))
                .unit(rs.getString("unit"))
                .warehouseQuantity(rs.getInt("warehouse_quantity"))
                .tradeCount(rs.getInt("trade_count"))
                .rating(rs.getLong("rating"))
                .attribute(rs.getString("attribute"))
                .previewUrl(rs.getString("preview_url"))
                .status(rs.getString("status"))
                .topSale(rs.getString("top_sale"))
                .descriptionEn(rs.getString("description_en"))
                .descriptionVi(rs.getString("description_vi"))
                .gameId(rs.getLong("game_id"))
                .build());
    }
    public List<PackageDTO> getAllActivePackage() {

        String sql = "SELECT   gp.id ,gp.description_vi,gp.description_en ,gp.attribute ,gp.game_id ,gp.name ,gp.price ,gp.rating ,gp.warehouse_quantity ,gp.unit ,gp.trade_count, f.preview_url ,gp.status, gp.top_sale from package gp \n" +
                        "\tjoin file f on f.uniq_id  =gp.image_id join game g on g.id = gp.game_id where g.status = 'ACTIVE' and gp.status= 'ACTIVE'";

        System.out.println(sql);

        return jdbcTemplate.query(sql, (rs, rowNum) -> PackageDTO.
                builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .price(rs.getLong("price"))
                .unit(rs.getString("unit"))
                .warehouseQuantity(rs.getInt("warehouse_quantity"))
                .tradeCount(rs.getInt("trade_count"))
                .rating(rs.getLong("rating"))
                .attribute(rs.getString("attribute"))
                .previewUrl(rs.getString("preview_url"))
                .status(rs.getString("status"))
                .topSale(rs.getString("top_sale"))
                .descriptionEn(rs.getString("description_en"))
                .descriptionVi(rs.getString("description_vi"))
                .gameId(rs.getLong("game_id"))
                .build());
    }

    public List<PackageDTO> getTopSale() {

        String sql = "SELECT   gp.id ,gp.description_vi,gp.description_en ,gp.attribute ,gp.game_id ,gp.name ,gp.price ,gp.rating ,gp.warehouse_quantity ,gp.unit ,gp.trade_count, f.preview_url ,ser.server from package gp \n" +
                "\tjoin file f on f.uniq_id  = gp.image_id join game g on g.id = gp.game_id " +
                "left join (SELECT package_id,\n" +
                "   GROUP_CONCAT(name SEPARATOR ', ') server\n" +
                "FROM color gsg   \n" +
                "GROUP BY package_id) ser on ser.package_id = gp.id where gp.top_sale = 'ACTIVE' and g.status = 'ACTIVE' ";

        System.out.println(sql);

        return jdbcTemplate.query(sql, (rs, rowNum) -> PackageDTO.
                builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .price(rs.getLong("price"))
                .unit(rs.getString("unit"))
                .gameId(rs.getLong("game_id"))
                .warehouseQuantity(rs.getInt("warehouse_quantity"))
                .tradeCount(rs.getInt("trade_count"))
                .rating(rs.getLong("rating"))
                .attribute(rs.getString("attribute"))
                .previewUrl(rs.getString("preview_url"))
                .build());
    }
    public List<PackageDTO> getPackageByGameId(Long gameId) {


        String sql = "SELECT   gp.id ,gp.description_vi,gp.description_en ,gp.attribute ,gp.game_id ,gp.name ,gp.price ,gp.rating ,gp.warehouse_quantity ,gp.unit ,gp.trade_count, f.preview_url ,ser.server from package gp \n" +
                "\tjoin file f on f.uniq_id = gp.image_id join game g on g.id = gp.game_id " +
                "left join (SELECT package_id,\n" +
                "   GROUP_CONCAT(name SEPARATOR ', ') server\n" +
                "FROM color gsg   \n" +
                "GROUP BY package_id) ser on ser.package_id = gp.id where gp.status = 'ACTIVE' and g.status = 'ACTIVE' and gp.game_id = "+gameId;

        System.out.println(sql);

        return jdbcTemplate.query(sql, (rs, rowNum) -> PackageDTO.
                builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .price(rs.getLong("price"))
                .unit(rs.getString("unit"))
                .warehouseQuantity(rs.getInt("warehouse_quantity"))
                .tradeCount(rs.getInt("trade_count"))
                .rating(rs.getLong("rating"))
                .attribute(rs.getString("attribute"))
                .previewUrl(rs.getString("preview_url"))
                .descriptionEn(rs.getString("description_en"))
                .descriptionVi(rs.getString("description_vi"))
                .gameId(rs.getLong("game_id"))
                .listServer(rs.getString("server"))
                .build());
    }
    public List<PackageDTO> getNewPackage() {

        String sql = "SELECT   gp.id ,gp.description_vi,gp.description_en ,gp.attribute ,gp.game_id ,gp.name ,gp.price ,gp.rating ,gp.warehouse_quantity ,gp.unit ,gp.trade_count, f.preview_url, ser.server  from package gp \n" +
                "\tjoin file f on f.uniq_id  =gp.image_id join game g on g.id = gp.game_id" +
                " left join (SELECT package_id,\n" +
                "   GROUP_CONCAT(name SEPARATOR ', ') server\n" +
                "FROM color gsg   \n" +
                "GROUP BY package_id) ser on ser.package_id = gp.id where gp.status = 'ACTIVE' and g.status = 'ACTIVE' order by gp.created_at desc limit 10";

        System.out.println(sql);

        return jdbcTemplate.query(sql, (rs, rowNum) -> PackageDTO.
                builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .price(rs.getLong("price"))
                .unit(rs.getString("unit"))
                .warehouseQuantity(rs.getInt("warehouse_quantity"))
                .tradeCount(rs.getInt("trade_count"))
                .rating(rs.getLong("rating"))
                .attribute(rs.getString("attribute"))
                .previewUrl(rs.getString("preview_url"))
                .descriptionEn(rs.getString("description_en"))
                .descriptionVi(rs.getString("description_vi"))
                .gameId(rs.getLong("game_id"))
                .listServer(rs.getString("server"))
                .build());
    }

    public List<PackageDTO> getBestSale() {

        String sql = "SELECT   gp.id ,gp.description_vi,gp.description_en ,gp.attribute ,gp.game_id ,gp.name ,gp.price ,gp.rating ,gp.warehouse_quantity ,gp.unit ,gp.trade_count, f.preview_url, ser.server  from package gp\n" +
                "           join file f on f.uniq_id  =gp.image_id join game g on g.id = gp.game_id " +
                "left join (SELECT package_id,\n" +
                "   GROUP_CONCAT(name SEPARATOR ', ') server\n" +
                "FROM color gsg   \n" +
                "GROUP BY package_id)ser on ser.package_id = gp.id where gp.status = 'ACTIVE' and g.status = 'ACTIVE'  order by gp.trade_count desc limit 3";

        System.out.println(sql);

        return jdbcTemplate.query(sql, (rs, rowNum) -> PackageDTO.
                builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .price(rs.getLong("price"))
                .unit(rs.getString("unit"))
                .warehouseQuantity(rs.getInt("warehouse_quantity"))
                .tradeCount(rs.getInt("trade_count"))
                .rating(rs.getLong("rating"))
                .attribute(rs.getString("attribute"))
                .previewUrl(rs.getString("preview_url"))
                .descriptionEn(rs.getString("description_en"))
                .descriptionVi(rs.getString("description_vi"))
                .gameId(rs.getLong("game_id"))
                .listServer(rs.getString("server"))
                .build());
    }
}
