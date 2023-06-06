package com.ivchern.exchange_employers.Repositories;

import com.ivchern.exchange_employers.Model.Team.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    @Query(value = "SELECT s FROM skill s WHERE s.skill_name = ?1", nativeQuery = true)
    Optional<Skill> findByName(String skillName);

    @Query(value = "SELECT * FROM skill s WHERE s.skill_name in (:names)", nativeQuery = true)
    Set<Skill> findByNames(@Param("names") Set<String> skillNames);

    Set<String> findSkillsById(Long id);
}


