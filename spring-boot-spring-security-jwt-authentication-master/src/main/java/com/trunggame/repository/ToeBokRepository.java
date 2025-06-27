package com.trunggame.repository;

import com.trunggame.models.PromotionUser;
import com.trunggame.models.ToeBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToeBokRepository extends JpaRepository<ToeBox, Long> {


}
