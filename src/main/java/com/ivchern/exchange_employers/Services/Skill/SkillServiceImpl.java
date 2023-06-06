package com.ivchern.exchange_employers.Services.Skill;

import com.ivchern.exchange_employers.Model.Team.Skill;
import com.ivchern.exchange_employers.Repositories.SkillRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class SkillServiceImpl implements SkillService {

    private SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public Optional<Skill> findById(Long id) {
        log.info("Find skill - {Find skill - {id}", id);
        return skillRepository.findById(id);
    }

    @Override
    public Optional<Skill> findByName(String name) {
        return skillRepository.findByName(name);
    }

    @Override
    public Iterable<Skill> findAll() {
        log.info("Find skills");
        return skillRepository.findAll();
    }
}