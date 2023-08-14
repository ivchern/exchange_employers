package com.ivchern.exchange_employers.Services.Skill;

import com.ivchern.exchange_employers.Common.Exception.IllegalArgument;
import com.ivchern.exchange_employers.DTO.SkillDTO.SkillDtoOnSave;
import com.ivchern.exchange_employers.Model.Team.Skill;
import com.ivchern.exchange_employers.Repositories.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public Optional<Skill> findById(Long id) {
        return skillRepository.findById(id);
    }

    @Override
    public Optional<Skill> findByName(String name) {
        return skillRepository.findByName(name);
    }

    @Override
    public Skill save(SkillDtoOnSave skillDto){
        if(skillDto.getName() == null || skillDto.getName().isEmpty()){
            throw new IllegalArgument("Name cannot be empty");
        }
        if(skillDto.getDescription() == null || skillDto.getDescription().isEmpty()){
            throw new IllegalArgument("Description cannot be empty");
        }
        var skillOpt = skillRepository.findByName(skillDto.getName());
        skillOpt.orElseThrow(
                () -> new IllegalArgument("Skill with name " + skillDto.getName() + " already exists")
        );
        Skill skill = new Skill(
                0L,
                skillDto.getName(),
                skillDto.getDescription()
        );
        return skillRepository.save(skill);
    }

    @Override
    public void delete(Long id) {
        if(!skillRepository.existsById(id)){
            throw new IllegalArgumentException("Skill with id " + id + " does not exist");
        }
        skillRepository.deleteById(id);
    }

    @Override
    public Iterable<Skill> findAll() {
        return skillRepository.findAll();
    }
}