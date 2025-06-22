package com.trunggame.repository;

import com.trunggame.models.Materials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialsRepository extends JpaRepository<Materials, Long> {

    List<Materials> findAllByIdIn(List<Long> ids);
// hiên tại bỏ vi đang dùng GameServerGroup
}
