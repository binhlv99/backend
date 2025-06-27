package com.trunggame.repository;

import com.trunggame.models.Promotion;
import com.trunggame.models.PromotionUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionUserRepository extends JpaRepository<PromotionUser, Long> {

    List<PromotionUser>  findBannerByStatus(PromotionUser.Status status);
}
