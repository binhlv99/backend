package com.trunggame.repository;

import com.trunggame.models.CountryGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryGroupRepository extends JpaRepository<CountryGroup, Long> {
    List<CountryGroup> findAllByGameId(Long gameId);
    List<CountryGroup> findAllByPackageId(Long gameId);
}
