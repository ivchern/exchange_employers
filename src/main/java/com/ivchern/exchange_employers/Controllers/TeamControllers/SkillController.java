package com.ivchern.exchange_employers.Controllers.TeamControllers;

import com.ivchern.exchange_employers.Model.Team.Skill;
import com.ivchern.exchange_employers.Services.Skill.SkillService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/skill")
@SecurityRequirement(name = "JWT")
@Tag(name = "Skill", description = "Навыки")
public class SkillController {
    private SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public Iterable<Skill> getSkills() {
        return skillService.findAll();
    }

}
