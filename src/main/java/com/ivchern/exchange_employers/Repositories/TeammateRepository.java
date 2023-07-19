package com.ivchern.exchange_employers.Repositories;

import com.ivchern.exchange_employers.Model.Card.Resource;
import com.ivchern.exchange_employers.Model.Team.Teammate;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeammateRepository extends CrudRepository<Teammate, Long>, JpaSpecificationExecutor<Teammate> {
    @Query(value = "SELECT teammate.* FROM teammate teammate \n" +
            "LEFT JOIN teammate_skill teammate_skill ON teammate_skill.teammate_id = teammate.id \n" +
            "LEFT JOIN skill skill ON skill.id = teammate_skill.skill_id \n" +
            "WHERE skill.skill_name IN (:skills)" , nativeQuery = true)
    Iterable<Teammate> findByNameSkills(@Param("skills") List<String> namesSkills);

    Iterable<Teammate> findByOwnerId(Long id);
}
