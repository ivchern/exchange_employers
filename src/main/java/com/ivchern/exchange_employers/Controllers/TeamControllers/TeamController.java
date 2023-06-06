package com.ivchern.exchange_employers.Controllers.TeamControllers;

import com.ivchern.exchange_employers.Model.Team.Team;
import com.ivchern.exchange_employers.Model.Team.Teammate;
import com.ivchern.exchange_employers.Services.Team.TeamService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/team")
@SecurityRequirement(name = "JWT")
@Tag(name = "Team", description = "Команда")
public class TeamController {

    private TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }


    @GetMapping
    Iterable<Team> getTeammate(){
        return teamService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable("id") Long id){
        Optional<Team> optTeam = teamService.findById(id);
        if (optTeam.isPresent()){
            return new ResponseEntity<Team>(optTeam.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
