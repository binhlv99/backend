package com.trunggame.repository;

import com.trunggame.enums.ECategoryStatus;
import com.trunggame.models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, Long> {

    @Query(name = "SELECT * FROM categories WHERE name = :name and status =  :status", nativeQuery = true)
    Optional<Categories> findOneByNameAndStatus(String name, ECategoryStatus status);
    Optional<List<Categories>> findAllByStatus(ECategoryStatus status);
    Optional<List<Categories>> findAllByParentId(Long parentId);
    Optional<Categories> findFirstByName(String name);
}
