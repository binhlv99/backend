package com.trunggame.repository;

import com.trunggame.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findAllByGameId(Long gameId);

    List<OrderDetail> findAllByGameOrderId(Long gameOrderId);

    @Transactional
    @Modifying
    void deleteAllByGameOrderId(Long gameOrderId);

}