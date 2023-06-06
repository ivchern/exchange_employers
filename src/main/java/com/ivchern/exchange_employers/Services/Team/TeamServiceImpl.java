package com.ivchern.exchange_employers.Services.Team;

import com.ivchern.exchange_employers.DTO.TeamDTO.TeamDTO;
import com.ivchern.exchange_employers.Model.Status;
import com.ivchern.exchange_employers.Model.Team.Team;
import com.ivchern.exchange_employers.Repositories.TeamRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
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
    public Team save(TeamDTO teamDTO) {
        ModelMapper modelMapper = new ModelMapper();

        Team team = modelMapper.map(teamDTO, Team.class);
        team.setCreated(LocalDateTime.now());
        team.setUpdated(LocalDateTime.now());
        team.setStatus(Status.ACTIVE);
        return teamRepository.save(team);
    }

    @Override
    public void delete(Long id) {

    }
}
