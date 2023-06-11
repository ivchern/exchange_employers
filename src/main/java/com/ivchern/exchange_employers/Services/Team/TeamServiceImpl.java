package com.ivchern.exchange_employers.Services.Team;

import com.ivchern.exchange_employers.DTO.TeamDTO.TeamDTO;
import com.ivchern.exchange_employers.Model.Status;
import com.ivchern.exchange_employers.Model.Team.Team;
import com.ivchern.exchange_employers.Repositories.TeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class TeamServiceImpl implements TeamService{

    private TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public Optional<Team> findById(Long id) {
        return teamRepository.findById(id);
    }

    @Override
    public Iterable<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public TeamDTO save(TeamDTO teamDTO, Long ownerId) {
        if(teamDTO.getName() == null) {
            return new TeamDTO("", "");
        }
        ModelMapper modelMapper = new ModelMapper();
        Team team = modelMapper.map(teamDTO, Team.class);
        team.setOwnerId(ownerId);
        team.setStatus(Status.ACTIVE);
        team.setId(ownerId);
        team.setCreated(LocalDateTime.now());
        team.setUpdated(LocalDateTime.now());
        teamRepository.save(team);
        return teamDTO;
    }
    @Override
    public TeamDTO update(TeamDTO teamDTO, Long ownerId) {
        if (teamDTO.getName() != null) {
            teamRepository.saveNameByOwnerId(teamDTO.getName(), ownerId, LocalDateTime.now());
        }else {
            teamDTO.setName(teamRepository.getNameById(ownerId));
        }
        if (teamDTO.getDescription() != null) {
            teamRepository.saveDescriptionByOwnerId(teamDTO.getDescription(), ownerId,  LocalDateTime.now());
        }else{
            teamDTO.setDescription(teamRepository.getDescriptionById(ownerId));
        }
        return teamDTO;
    }

    @Override
    public boolean existByOwnerId(Long ownerId) {
        int val = teamRepository.existByOwnerId(ownerId);
        if(val == 0) {
            return false;
        }
        return true;
    }

    @Override
    public void delete(Long id) {

    }
}
