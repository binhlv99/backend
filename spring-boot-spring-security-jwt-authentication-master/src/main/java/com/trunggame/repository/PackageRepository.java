package com.trunggame.repository;

import com.trunggame.models.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {
    boolean existsByGameId(String gameId);

    List<Package> findAllByGameId(Long gameId);

}