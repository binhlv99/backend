package com.trunggame.repository;

import com.trunggame.models.Banner;
import com.trunggame.models.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    List<Promotion>  findBannerByStatus(Promotion.Status status);
}
