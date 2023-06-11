package com.ivchern.exchange_employers.Services.Team;

import com.ivchern.exchange_employers.DTO.TeamDTO.TeamDTO;
import com.ivchern.exchange_employers.Model.Team.Team;
import com.ivchern.exchange_employers.Model.Team.Teammate;

import java.util.Optional;

public interface TeamService {
    Optional<Team> findById(Long id);
    Iterable<Team> findAll();
    TeamDTO save(TeamDTO teamDTO, Long ownerId);
    TeamDTO update(TeamDTO teamDTO, Long ownerId);
    boolean existByOwnerId(Long id);
    void delete(Long id);
}
