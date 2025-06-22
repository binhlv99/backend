package com.trunggame.repository;

import com.trunggame.models.ShoeOutsoles;
import com.trunggame.models.ShoeTops;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoeTopsRepository extends JpaRepository<ShoeTops, Long> {

    @Query(value = "SELECT * FROM smart_tag_game WHERE game_id = :gameId ", nativeQuery = true)
    Optional<List<ShoeTops>> findByGameId(Long gameId);

    @Query(value = "select st.* from shoe_outsoles st " +
            "join smart_tag_game stg on st.id = stg.tag_id " +
            "where game_id = :gameId", nativeQuery = true)
    List<ShoeOutsoles> findTagByGameId(Long gameId);
}
