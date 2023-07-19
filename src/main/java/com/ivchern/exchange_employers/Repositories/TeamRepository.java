package com.ivchern.exchange_employers.Repositories;

import com.ivchern.exchange_employers.Model.Team.Team;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {
    Optional<Team> findByName(String name);
    Optional<Team> findByOwnerId(Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Team t SET t.name = ?1, t.updated = ?3 WHERE t.ownerId = ?2")
    void saveNameByOwnerId(String name, Long ownerId, LocalDateTime now);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Team t SET t.description = ?1, t.updated = ?3 WHERE t.ownerId = ?2")
    void saveDescriptionByOwnerId(String description, Long ownerId, LocalDateTime now);

    @Query(value = "SELECT t.description FROM Team t WHERE t.id = ?1", nativeQuery = true)
    String getDescriptionById(Long ownerId);

    @Query(value = "SELECT t.name FROM Team t WHERE t.id = ?1", nativeQuery = true)
    String getNameById(Long ownerId);

    @Query(value = "SELECT CASE WHEN COUNT(t) > 0 THEN 1 ELSE 0 END FROM Team t WHERE t.id = ?1")
    int existByOwnerId(Long ownerId);
}
