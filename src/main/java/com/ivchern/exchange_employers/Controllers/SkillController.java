package com.ivchern.exchange_employers.Controllers;

import com.ivchern.exchange_employers.DTO.SkillDTO.SkillDtoOnSave;
import com.ivchern.exchange_employers.Model.Team.Skill;
import com.ivchern.exchange_employers.Services.Skill.SkillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/skill")
@SecurityRequirement(name = "JWT")
@Tag(name = "Skill", description = "Навыки")
public class SkillController {
    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public Iterable<Skill> getSkills() {
        return skillService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    @Operation(description = "Добавление скила. Только с полномочиями ROLE_ADMIN или ROLE_MODERATOR")
    public Skill createSkill(@RequestBody SkillDtoOnSave skill) {
        return skillService.save(skill);
    }
}
