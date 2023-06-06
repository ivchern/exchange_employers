package com.ivchern.exchange_employers.Services.Skill;
import com.ivchern.exchange_employers.Model.Team.Skill;

import java.util.Optional;

public interface SkillService {
    Optional<Skill> findById(Long id);
    Optional<Skill> findByName(String name);
    Iterable<Skill> findAll();
}
