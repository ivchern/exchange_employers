package com.ivchern.exchange_employers.Services.Card.RequestWorker;

import com.ivchern.exchange_employers.Common.Exception.ForbiddenException;
import com.ivchern.exchange_employers.Common.Exception.IllegalArgument;
import com.ivchern.exchange_employers.Common.Exception.NotFoundException;
import com.ivchern.exchange_employers.DTO.CardDTO.RequestDTO.RequestWorkerDtoOnRequest;
import com.ivchern.exchange_employers.DTO.CardDTO.RequestDTO.RequestWorkerDtoOnSave;
import com.ivchern.exchange_employers.Model.Card.RequestWorker;
import com.ivchern.exchange_employers.Model.Status;
import com.ivchern.exchange_employers.Model.Team.Skill;
import com.ivchern.exchange_employers.Repositories.RequestWorkerRepository;
import com.ivchern.exchange_employers.Security.Services.SecurityService;
import com.ivchern.exchange_employers.Services.Recommendation.RecommendationMapper;
import com.ivchern.exchange_employers.Services.Recommendation.RecommendationService;
import com.ivchern.exchange_employers.Services.Skill.SkillService;
import com.ivchern.exchange_employers.Services.User.OwnerDetailServiceImpl;
import com.ivchern.grpc.Recommendations;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class RequestWorkerServiceImpl implements RequestWorkerService {
    private final RequestWorkerRepository requestWorkerRepository;
    private final SkillService skillService;
    private final OwnerDetailServiceImpl ownerDetailService;
    private final SecurityService securityService;
    private final RecommendationMapper recommendationMapper;
    private final RecommendationService recommendationService;

    public RequestWorkerServiceImpl(RequestWorkerRepository requestWorkerRepository, SkillService skillService, OwnerDetailServiceImpl ownerDetailService, SecurityService securityService, RecommendationMapper recommendationMapper, RecommendationService recommendationService) {
        this.requestWorkerRepository = requestWorkerRepository;
        this.skillService = skillService;
        this.ownerDetailService = ownerDetailService;
        this.securityService = securityService;
        this.recommendationMapper = recommendationMapper;
        this.recommendationService = recommendationService;
    }

    @Override
    @Transactional
    public RequestWorkerDtoOnRequest save(RequestWorkerDtoOnSave requestDto) {
        ModelMapper modelMapper = new ModelMapper();
        RequestWorker requestWorker = modelMapper.map(requestDto, RequestWorker.class);

        if(requestWorker.getJobTitle().isEmpty()){
            throw new IllegalArgument("Job title not found");
        }

        if(requestDto.getOwnerId() == null){
            throw new IllegalArgument("Owner a card not found");
        }

        requestWorker.setOwnerDetails(ownerDetailService.findByOwnerId(requestDto.getOwnerId()).orElseThrow(
                () -> new IllegalArgument("Owner a card not found")
        ));

        Set<Skill> skills = new HashSet<>();
        for (Skill skill : requestDto.getSkills()) {
            Optional<Skill> skillOpt = skillService.findByName(skill.getSkill());
            skills.add(skillOpt.orElseThrow( () -> new IllegalArgument("Skill not found: " + skill.getSkill())));
        }

        requestWorker.setSkills(skills);
        requestWorker.setStatus(Status.ACTIVE);
        requestWorker.setId(0L);

        LocalDateTime dateCreate = LocalDateTime.now();
        requestWorker.setCreated(dateCreate);
        requestWorker.setUpdated(dateCreate);

        return RequestWorkerMapper.mapEntityIntoDTO(requestWorkerRepository.save(requestWorker));
    }

    @Override
    public RequestWorkerDtoOnRequest update(Long id, RequestWorkerDtoOnSave requestWorker, Principal principal) {
        var savedRequestOpt = requestWorkerRepository.findById(id);
        if(savedRequestOpt.isPresent()) {
            if (!securityService.isOwner(savedRequestOpt.get().getOwnerDetails().getId(), principal))
                throw new ForbiddenException("There is no access to change the card");
        }
        var savedRequest = savedRequestOpt.orElseThrow(() ->
                new NotFoundException("Card not found with id: " + id)
        );

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
            savedRequest.setOwnerDetails(ownerDetail);
        }

        Set<Skill> skills = new HashSet<>();
        for (Skill skill : requestWorker.getSkills()) {
            Optional<Skill> skillOpt = skillService.findByName(skill.getSkill());
            skills.add(skillOpt.orElseThrow( () -> new IllegalArgument("Skill not found: "  + skill.getSkill())));
        }
        savedRequest.setSkills(skills);

        savedRequest.setUpdated(LocalDateTime.now());
        return RequestWorkerMapper.mapEntityIntoDTO(requestWorkerRepository.save(savedRequest));
    }

    @Override
    public RequestWorkerDtoOnRequest findById(Long id) {
        ModelMapper modelMapper = new ModelMapper();
        var requestWorker = requestWorkerRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Card not found with id: " + id)
        );
        var requestWorkerDtoOnRequest = modelMapper.map(requestWorker, RequestWorkerDtoOnRequest.class);
        var ownerDetail = ownerDetailService.findByOwnerId(requestWorker.getOwnerDetails().getId()).orElseThrow(
                () -> new NotFoundException("Owner not found")
        );
        requestWorkerDtoOnRequest.setOwnerDetail(ownerDetail);
        return requestWorkerDtoOnRequest;
    }

    @Override
    public void delete(Long id, Principal principal) {
        var requestWorker = requestWorkerRepository.findById(id);
        if(requestWorker.isPresent()) {
            if (!securityService.isOwner(requestWorker.get().getOwnerDetails().getId(), principal))
                throw new ForbiddenException("There is no access to change the card");
        }

        if (requestWorker.isEmpty()) {
            throw new NotFoundException("Card not found with id: " + id);
        }
        requestWorkerRepository.delete(requestWorker.get());
    }

    @Override
    public Page<RequestWorkerDtoOnRequest> findAll(Specification<RequestWorker> specRequest, Pageable paging) {
        Page<RequestWorker> requestWorkerPage = requestWorkerRepository.findAll(specRequest, paging);
        return RequestWorkerMapper.mapEntityPageIntoDTOPage(paging, requestWorkerPage);
    }

    @Override
    public List<RequestWorker> getRecommendationById(Long id) {
        var requestWorkers = requestWorkerRepository.findAll();
        var requestList= recommendationMapper.CreateRequestFromRequestWorker(id, requestWorkers);
        Recommendations.CardResponse recommendationIds = recommendationService.getRecommendation(requestList);
        List<Long> selectedCardIds = recommendationIds.getSelectedCardIdList();
        List<RequestWorker> matchingRequestWorkers = new ArrayList<>();
        var countCard = (long) selectedCardIds.size();
        countCard = countCard < 10 ? countCard : countCard;
        for(int i = 0; i < countCard; i++) {
            var idRecommendationCard = selectedCardIds.get(i);
            for (RequestWorker requestWorker : requestWorkers) {
                if (requestWorker.getId().equals(idRecommendationCard)) {
                    matchingRequestWorkers.add(requestWorker);
                    break;
                }
            }
        }
        return matchingRequestWorkers;
    }
}
