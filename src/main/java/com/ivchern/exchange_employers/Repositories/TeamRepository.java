package com.ivchern.exchange_employers.Repositories;

import com.ivchern.exchange_employers.Model.Team.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TeamRepository extends CrudRepository<Team, Long> {
    Optional<Team> findByName(String name);
    Optional<Team> findByOwnerId(Long id);
}
