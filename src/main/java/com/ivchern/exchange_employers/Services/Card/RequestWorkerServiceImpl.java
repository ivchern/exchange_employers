package com.ivchern.exchange_employers.Services.Card;

import com.ivchern.exchange_employers.Common.Exception.IllegalArgument;
import com.ivchern.exchange_employers.Common.Exception.NotFoundException;
import com.ivchern.exchange_employers.DTO.CardDTO.RequestDTO.RequestWorkerDtoOnRequest;
import com.ivchern.exchange_employers.DTO.CardDTO.RequestDTO.RequestWorkerDtoOnSave;
import com.ivchern.exchange_employers.Model.Card.RequestWorker;
import com.ivchern.exchange_employers.Model.Status;
import com.ivchern.exchange_employers.Model.Team.Skill;
import com.ivchern.exchange_employers.Model.User.OwnerDetails;
import com.ivchern.exchange_employers.Repositories.RequestWorkerRepository;
import com.ivchern.exchange_employers.Services.Skill.SkillService;
import com.ivchern.exchange_employers.Services.User.OwnerDetailServiceImpl;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class RequestWorkerServiceImpl implements RequestWorkerService {

    private final RequestWorkerRepository requestWorkerRepository;
    private final SkillService skillService;
    private final OwnerDetailServiceImpl ownerDetailService;

    public RequestWorkerServiceImpl(RequestWorkerRepository requestWorkerRepository, SkillService skillService, OwnerDetailServiceImpl ownerDetailService) {
        this.requestWorkerRepository = requestWorkerRepository;
        this.skillService = skillService;
        this.ownerDetailService = ownerDetailService;
    }

    @Override
    public List<RequestWorkerDtoOnRequest> findAll(Pageable paging) {
        List<RequestWorker> requestWorker = requestWorkerRepository.findAll(paging);
        ModelMapper  modelMapper = new ModelMapper();

        List<RequestWorkerDtoOnRequest> requestWorkerDtoOnRequest = new ArrayList<>();
        for (RequestWorker request : requestWorker) {
            var requestDto = modelMapper.map(request, RequestWorkerDtoOnRequest.class);
            Optional<OwnerDetails> userDetailOpt = ownerDetailService.findByOwnerId(request.getOwnerId());
            userDetailOpt.ifPresent(requestDto::setOwnerDetail);
            requestWorkerDtoOnRequest.add(requestDto);
        }
        return requestWorkerDtoOnRequest;
    }

    @Override
    @Transactional
    public RequestWorker save(RequestWorkerDtoOnSave requestDto) {
        ModelMapper modelMapper = new ModelMapper();
        RequestWorker requestWorker = modelMapper.map(requestDto, RequestWorker.class);

        if(requestWorker.getJobTitle().isEmpty()){
            throw new IllegalArgument("Job title not found");
        }

        if(requestWorker.getOwnerId() == null){
            throw new IllegalArgument("Owner a card not found");
        }

        ownerDetailService.findByOwnerId(requestWorker.getOwnerId()).orElseThrow(
                () -> new IllegalArgument("Owner a card not found")
        );

        Set<Skill> skills = new HashSet<>();
        for (String skill : requestDto.getSkills()) {
            Optional<Skill> skillOpt = skillService.findByName(skill);
            skills.add(skillOpt.orElseThrow( () -> new IllegalArgument("Skill not found")));
        }

        requestWorker.setSkills(skills);
        requestWorker.setStatus(Status.ACTIVE);
        requestWorker.setId(0L);

        LocalDateTime dateCreate = LocalDateTime.now();
        requestWorker.setCreated(dateCreate);
        requestWorker.setUpdated(dateCreate);

        return requestWorkerRepository.save(requestWorker);
    }

    @Override
    public RequestWorker update(Long id, RequestWorkerDtoOnSave requestWorker) {
        var savedRequest = requestWorkerRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Card not found"));

        if(requestWorker.getJobTitle() != null){
            savedRequest.setJobTitle(requestWorker.getJobTitle());
        }

        if(requestWorker.getProjectName() != null){
            savedRequest.setProjectName(requestWorker.getProjectName());
        }

        if(requestWorker.getRank() != null){
            savedRequest.setRank(requestWorker.getRank());
        }

        if(requestWorker.getDescription() != null){
            savedRequest.setDescription(requestWorker.getDescription());
        }

        if(requestWorker.getNeedBefore() != null){
            savedRequest.setNeedBefore(requestWorker.getNeedBefore());
        }

        if(requestWorker.getOwnerId() != null) {
             var ownerDetail = ownerDetailService.findByOwnerId(requestWorker.getOwnerId()).orElseThrow(
                    () -> new NotFoundException("Owner not found")
            );
            savedRequest.setOwnerId(requestWorker.getOwnerId());
        }

        Set<Skill> skills = new HashSet<>();
        for (String skill : requestWorker.getSkills()) {
            Optional<Skill> skillOpt = skillService.findByName(skill);
            skills.add(skillOpt.orElseThrow( () -> new IllegalArgument("Skill not found")));
        }
        savedRequest.setSkills(skills);

        savedRequest.setUpdated(LocalDateTime.now());
        return requestWorkerRepository.save(savedRequest);
    }

    @Override
    public RequestWorkerDtoOnRequest findById(Long id) {
        ModelMapper modelMapper = new ModelMapper();
        var requestWorker = requestWorkerRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Card not found")
        );
        var requestWorkerDtoOnRequest = modelMapper.map(requestWorker, RequestWorkerDtoOnRequest.class);
        var ownerDetail = ownerDetailService.findByOwnerId(requestWorker.getOwnerId()).orElseThrow(
                () -> new NotFoundException("Owner not found")
        );
        requestWorkerDtoOnRequest.setOwnerDetail(ownerDetail);
        return requestWorkerDtoOnRequest;
    }

    @Override
    public void delete(Long id) {
        var requestWorker = requestWorkerRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Card not found")
        );
        requestWorkerRepository.delete(requestWorker);
    }

    @Override
    public List<RequestWorkerDtoOnRequest> findAll(Specification<RequestWorker> specRequest, Pageable paging) {
        var requestWorker = requestWorkerRepository.findAll(specRequest, paging);
        ModelMapper modelMapper = new ModelMapper();
        List<RequestWorkerDtoOnRequest> requestWorkerDtoOnRequest = new ArrayList<>();
        for (RequestWorker request : requestWorker) {
            var requestDto = modelMapper.map(request, RequestWorkerDtoOnRequest.class);
            Optional<OwnerDetails> userDetailOpt = ownerDetailService.findByOwnerId(request.getOwnerId());
            userDetailOpt.ifPresent(requestDto::setOwnerDetail);
            requestWorkerDtoOnRequest.add(requestDto);
        }
        return requestWorkerDtoOnRequest;
    }
}
