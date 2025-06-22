package com.trunggame.repository;

import com.trunggame.models.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
    List<Color> findAllByGameId(Long gameId);
    List<Color> findAllByPackageId(Long gameId);
}
