package com.ivchern.exchange_employers.Controllers.TeamControllers;

import com.ivchern.exchange_employers.DTO.TeamDTO.TeammateDTO;
import com.ivchern.exchange_employers.Model.Team.Teammate;
import com.ivchern.exchange_employers.Services.Teammate.TeammateService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/teammate")
@SecurityRequirement(name = "JWT")
@Tag(name = "Teammate", description = "Тимейты")
public class TeammateController {

    private TeammateService teammateService;

    public TeammateController(TeammateService teammateService) {
        this.teammateService = teammateService;
    }

    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    Teammate postTeammate(@RequestBody TeammateDTO teammateDTO){
        return teammateService.save(teammateDTO);
    }

    @GetMapping
    public Iterable<Teammate> getTeammate(){
        return teammateService.findAll();
    }

    @GetMapping(path = "GetBySkills", params = "skill")
    public Iterable<Teammate> getListBySkills(@RequestParam(value = "skill") List<String> skills){
        return teammateService.findBySkills(skills);
    }

    @PutMapping(path ="/id", consumes = "application/json")
    public Teammate putTeammate(@RequestBody Teammate teammate,@RequestParam Long id){
        return teammateService.update(teammate, id);
    }

    @GetMapping(path = "id")
    public ResponseEntity<Teammate> getTeammateById(@RequestParam Long id){
        Optional<Teammate> optTeammate = teammateService.findById(id);
        if (optTeammate.isPresent()){
            return new ResponseEntity<Teammate>(optTeammate.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "GetByOwnerId", params = "id")
    public Iterable<Teammate> getTeammateByOwnerId(@RequestParam Long id){
        return teammateService.findByOwnerId(id);
    }
    //TODO:add getByOwnerId
    @DeleteMapping(path = "id", consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTeammate(@RequestParam Long id){
        try {

            teammateService.delete(id);
        }catch (EmptyResultDataAccessException e) {};
    }
}
