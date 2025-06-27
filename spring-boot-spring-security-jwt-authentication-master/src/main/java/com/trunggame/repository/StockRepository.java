package com.trunggame.repository;

import com.trunggame.models.ProductType;
import com.trunggame.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock>  findBannerByStatus(Stock.Status status);
}
