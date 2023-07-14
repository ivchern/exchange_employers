package com.ivchern.exchange_employers.Services.Card;

import com.ivchern.exchange_employers.DTO.CardDTO.RequestWorkerDtoOnCreate;
import com.ivchern.exchange_employers.Model.Card.RequestWorker;
import com.ivchern.exchange_employers.Model.Status;
import com.ivchern.exchange_employers.Model.Team.Skill;
import com.ivchern.exchange_employers.Model.User.OwnerDetail;
import com.ivchern.exchange_employers.Repositories.RequestWorkerRepository;
import com.ivchern.exchange_employers.Repositories.SkillRepository;
import com.ivchern.exchange_employers.Repositories.UserDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class RequestWorkerServiceImpl implements RequestWorkerService {

    private RequestWorkerRepository requestWorkerRepository;
    private SkillRepository skillRepository;
    private UserDetailsRepository userDetailsRepository;

    public RequestWorkerServiceImpl(RequestWorkerRepository requestWorkerRepository, SkillRepository skillRepository, UserDetailsRepository userDetailsRepository) {
        this.requestWorkerRepository = requestWorkerRepository;
        this.skillRepository = skillRepository;
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public Iterable<RequestWorker> findAll() {
        return requestWorkerRepository.findAll();
    }

    @Override
    public RequestWorker save(RequestWorkerDtoOnCreate requestDto) {
        ModelMapper modelMapper = new ModelMapper();
        RequestWorker requestWorker = modelMapper.map(requestDto, RequestWorker.class);
        Set<Skill> skills = skillRepository.findByNames(requestDto.getSkills());
        requestWorker.setSkills(skills);
        requestWorker.setCreated(LocalDateTime.now());
        requestWorker.setStatus(Status.ACTIVE);
        requestWorker.setId(0L);

        var userDetailOpt = userDetailsRepository.findById(requestDto.getOwnerId());
        OwnerDetail ownerDetail = userDetailOpt.get();
        requestWorker.setOwnerDetail(ownerDetail);
        return requestWorkerRepository.save(requestWorker);
    }

    @Override
    public RequestWorker update(RequestWorker requestWorker) {
        requestWorker.setUpdated(LocalDateTime.now());
        return requestWorkerRepository.save(requestWorker);
    }

    @Override
    public Optional<RequestWorker> findById(Long id) {
        return requestWorkerRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        Optional<RequestWorker> requestWorkerOpt = requestWorkerRepository.findById(id);
        if (requestWorkerOpt.isPresent()) {
            RequestWorker requestWorker = requestWorkerOpt.get();
            requestWorker.setUpdated(LocalDateTime.now());
            requestWorker.setStatus(Status.DELETED);
            requestWorkerRepository.save(requestWorker);
        }
    }

    @Override
    public Iterable<RequestWorker> findAll(Specification<RequestWorker> specRequest, Pageable paging) {
        return requestWorkerRepository.findAll(specRequest, paging);
    }
}
