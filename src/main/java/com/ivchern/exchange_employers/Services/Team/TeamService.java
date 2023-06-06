package com.ivchern.exchange_employers.Services.Team;

import com.ivchern.exchange_employers.DTO.TeamDTO.TeamDTO;
import com.ivchern.exchange_employers.Model.Team.Team;
import com.ivchern.exchange_employers.Model.Team.Teammate;

import java.util.Optional;

public interface TeamService {
    Optional<Team> findById(Long id);
    Iterable<Team> findAll();
    Team save(TeamDTO teamDTO);
    void delete(Long id);
}
