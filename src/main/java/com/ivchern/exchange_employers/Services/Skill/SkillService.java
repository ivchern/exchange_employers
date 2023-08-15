package com.ivchern.exchange_employers.Services.Skill;
import com.ivchern.exchange_employers.DTO.SkillDTO.SkillDtoOnSave;
import com.ivchern.exchange_employers.Model.Team.Skill;

import java.util.Optional;

public interface SkillService {
    Optional<Skill> findById(Long id);
    Optional<Skill> findByName(String name);
    Skill save(SkillDtoOnSave skillDto);
    void delete(Long id);
    Iterable<Skill> findAll();
}
