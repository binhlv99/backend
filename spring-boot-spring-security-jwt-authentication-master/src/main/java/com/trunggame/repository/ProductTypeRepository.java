package com.trunggame.repository;

import com.trunggame.models.Banner;
import com.trunggame.models.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {

    List<ProductType>  findBannerByStatus(ProductType.Status status);
}
