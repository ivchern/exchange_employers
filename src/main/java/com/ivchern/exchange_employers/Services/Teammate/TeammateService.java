package com.ivchern.exchange_employers.Services.Teammate;

import com.ivchern.exchange_employers.DTO.TeamDTO.TeammateDTO;
import com.ivchern.exchange_employers.Model.Team.Skill;
import com.ivchern.exchange_employers.Model.Team.Teammate;

import java.util.List;
import java.util.Optional;

public interface TeammateService {
    Optional<Teammate> findById(Long id);
    Iterable<Teammate> findBySkills(List<String> skills);
    Iterable<Teammate> findByOwnerId(Long id);
    Iterable<Teammate> findAll();
    Teammate save(TeammateDTO teammateDTO);
    Teammate update(Teammate teammate, Long id);
    void delete(Long id);

}
