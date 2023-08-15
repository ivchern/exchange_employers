package com.ivchern.exchange_employers.Controllers;

import com.ivchern.exchange_employers.Common.ExceptionResponse;
import com.ivchern.exchange_employers.DTO.SkillDTO.SkillDtoOnSave;
import com.ivchern.exchange_employers.Model.Team.Skill;
import com.ivchern.exchange_employers.Services.Skill.SkillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
    @Operation(description = "Поиск всех скиллов")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success!"),
                   @ApiResponse(responseCode = "401", description = "Api session missing, invalid or expired",
                            content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})})
    public Iterable<Skill> getSkills() {
        return skillService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    @Operation(description = "Добавление скила. Только с полномочиями ROLE_ADMIN или ROLE_MODERATOR")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Success!"),
            @ApiResponse(responseCode = "401", description = "Api session missing, invalid or expired",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Not enough permissions to use this endpoint",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Resource request card not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})})
    public Skill createSkill(@RequestBody SkillDtoOnSave skill) {
        return skillService.save(skill);
    }

    @DeleteMapping(path = "/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    @Operation(description = "Удаление скила. Только с полномочиями ROLE_ADMIN или ROLE_MODERATOR")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Success!"),
            @ApiResponse(responseCode = "401", description = "Api session missing, invalid or expired",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Not enough permissions to use this endpoint",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Resource request card not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})})
    public ResponseEntity<Void> deleteSkill(@PathVariable("id") Long id) {
        skillService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
