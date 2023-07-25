package com.ivchern.exchange_employers.Services.Card.Resource;

import com.ivchern.exchange_employers.Common.Exception.ForbiddenException;
import com.ivchern.exchange_employers.Common.Exception.IllegalArgument;
import com.ivchern.exchange_employers.Common.Exception.NotFoundException;
import com.ivchern.exchange_employers.DTO.CardDTO.ResourceDTO.ResourceDtoOnRequest;
import com.ivchern.exchange_employers.DTO.CardDTO.ResourceDTO.ResourceDtoOnSave;
import com.ivchern.exchange_employers.Model.Card.Resource;
import com.ivchern.exchange_employers.Model.Status;
import com.ivchern.exchange_employers.Repositories.ResourceRepository;
import com.ivchern.exchange_employers.Security.Services.SecurityService;
import com.ivchern.exchange_employers.Services.Teammate.TeammateService;
import com.ivchern.exchange_employers.Services.User.OwnerDetailService;
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
public class ResourceServiceImpl implements ResourceService {
    private final TeammateService teammateService;
    private final ResourceRepository resourceRepository;
    private final OwnerDetailService ownerDetailService;
    private final SecurityService securityService;

    public ResourceServiceImpl(TeammateService teammateService, ResourceRepository resourceRepository, OwnerDetailService ownerDetailService, SecurityService securityService) {
        this.teammateService = teammateService;
        this.resourceRepository = resourceRepository;
        this.ownerDetailService = ownerDetailService;
        this.securityService = securityService;
    }

    @Override
    public Resource update(ResourceDtoOnSave resourceDtoOnSave, Long id, Principal principal) {
        var resourceOpt = resourceRepository.findById(id);
        if(resourceOpt.isPresent()) {
            if (!securityService.isOwner(resourceOpt.get().getOwnerDetails().getId(), principal))
                throw new ForbiddenException("There is no access to change the card");
        }
        var resource = resourceOpt.orElseThrow(() ->
                new NotFoundException("Card not found with id: " + id)
        );

        if(resourceDtoOnSave.getCardTitle() == null) {
            throw new IllegalArgument("Card Title is null");
        }
        resource.setCardTitle(resourceDtoOnSave.getCardTitle());

        if(resourceDtoOnSave.getDescription() != null){
            resource.setDescription(resourceDtoOnSave.getDescription());
        }

        if(resourceDtoOnSave.getLocationWorked() != null) {
            resource.setLocationWorked(resourceDtoOnSave.getLocationWorked());
        }

        if(isDateConflict(resourceDtoOnSave.getFromFree(), resourceDtoOnSave.getEndFree())) {
            throw new IllegalArgument("Date not correct");
        }
        if(resourceDtoOnSave.getFromFree() != null){
            resource.setFromFree(resourceDtoOnSave.getFromFree());
        }

        if(resourceDtoOnSave.getEndFree() != null) {
            resource.setEndFree(resourceDtoOnSave.getEndFree());
        }

        if(resourceDtoOnSave.getTeammateId() != null){
            resource.setTeammate(teammateService.findById(resourceDtoOnSave.getTeammateId()).
                    orElseThrow( () -> new IllegalArgument("teammate not found with: " + resourceDtoOnSave.getTeammateId())
                            ));
        }

        if(resourceDtoOnSave.getOwnerId() != null){
            resource.setOwnerDetails(ownerDetailService.findByOwnerId(resourceDtoOnSave.getOwnerId()).orElseThrow(
                    () -> new IllegalArgument("Owner not found with id: " + resourceDtoOnSave.getOwnerId())
            ));
        }
        return resourceRepository.save(resource);
    }

    @Override
    public Page<ResourceDtoOnRequest> findAll(Specification<Resource> SpecRequest, Pageable page) {
        Page<Resource> resourcePage =  resourceRepository.findAll(SpecRequest, page);
        return ResourceMapper.mapEntityPageIntoDTOPage(page, resourcePage);
    }


    @Override
    public Resource save(ResourceDtoOnSave resourceDtoOnSave) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Resource resource = modelMapper.map(resourceDtoOnSave, Resource.class);

        if(resource.getCardTitle() == null){
            throw new IllegalArgument("Card Title is null");
        }

        var teammate = teammateService.findById(resourceDtoOnSave.getTeammateId()).orElseThrow(
                () -> new IllegalArgument("Teammate not found with id:  " + resourceDtoOnSave.getTeammateId())
        );
        resource.setTeammate(teammate);

        var ownerDetails = ownerDetailService.findByOwnerId(resourceDtoOnSave.getOwnerId()).orElseThrow(
                () -> new IllegalArgument("Owner not found with id: " + resourceDtoOnSave.getOwnerId())
        );

        if(isDateConflict(resourceDtoOnSave.getFromFree(), resourceDtoOnSave.getEndFree())) {
            throw new IllegalArgument("Date not correct");
        }

        resource.setOwnerDetails(ownerDetails);
        var created = LocalDateTime.now();
        resource.setCreated(created);
        resource.setUpdated(created);
        resource.setStatus(Status.ACTIVE);
        return resourceRepository.save(resource);
    }

    @Override
    public void delete(Long id, Principal principal) {
        var resourceOpt = resourceRepository.findById(id);
        if(resourceOpt.isPresent()) {
            if (!securityService.isOwner(resourceOpt.get().getOwnerDetails().getId(), principal))
                throw new ForbiddenException("There is no access to change the card");
        }

        if (resourceOpt.isEmpty()) {
            throw new NotFoundException("Card not found with id: " + id);
        }
        resourceRepository.delete(resourceOpt.get());
    }

    private boolean isDateConflict(Date startDate, Date endDate){
        return !startDate.before(endDate);
    }
}
//
//    @Override
//    public ResourceDtoOnRequest update(ResourceDtoOnRequest resourceDTO, Long Id) {
//        resourceDTO.setUpdated(LocalDateTime.now());
//
//        ModelMapper modelMapper = new ModelMapper();
//        Resource resource = modelMapper.map(resourceDTO, Resource.class);
//        resource.setUpdated(LocalDateTime.now());
//        Resource resourceUpd = resourceRepository.save(resource);
//        return getResourceDTOEntity(resourceUpd);
//    }
//
//    @Override
//    public List<ResourceDtoOnRequest> search(Specification<Resource> specResources, Specification<Skill> specSkill, Specification<Teammate> specTeammate, Pageable page) {
//        List<ResourceDtoOnRequest> resourceDTO =  new ArrayList<>();
//
//        Iterable<Resource> resource = resourceRepository.findAll(specResources, page);
//
//        resource.forEach(resource1 -> {
//            ResourceDtoOnRequest resourceWithTeammate = getResourceDTOEntity(resource1, specSkill, specTeammate);
//            if(resourceWithTeammate != null){
//                resourceDTO.add(resourceWithTeammate);
//            }
//        });
//        return resourceDTO;
//    }
//
//    @Override
//    public List<ResourceDtoOnRequest> findAll() {
//        List<ResourceDtoOnRequest> resourceDTO =  new ArrayList<>();
//
//        Iterable<Resource> resource = resourceRepository.findAll();
//
//        resource.forEach(resource1 -> {
//            resourceDTO.add(getResourceDTOEntity(resource1));
//        });
//        return resourceDTO;
//    }
//
//    @Override
//    public Optional<ResourceDtoOnRequest> findById(Long id) {
//        ResourceDtoOnRequest resourceDTO;
//        Optional<Resource> resourceOpt = resourceRepository.findById(id);
//        if (resourceOpt.isPresent()){
//            resourceDTO = getResourceDTOEntity(resourceOpt.get());
//            Optional<ResourceDtoOnRequest> res = Optional.of(resourceDTO);
//            return res;
//        }else{
//            return Optional.empty();
//        }
//    }
//
//    @Override
//    public void delete(Long id) {
//        Optional<Resource> resourceOpt = resourceRepository.findById(id);
//        if (resourceOpt.isPresent()){
//            Resource resource = resourceOpt.get();
//            resource.setStatus(Status.DELETED);
//        }
//    }
//
//    public ResourceDtoOnRequest getResourceDTOEntity(Resource resource,
//                                                     Specification<Skill> specSkill,Specification<Teammate> specTeammate) {
//        ModelMapper modelMapper = new ModelMapper();
//        ResourceDtoOnRequest resourceDTO = modelMapper.map(resource, ResourceDtoOnRequest.class);
//        List<Skill> skillFilter = new ArrayList<>();
//        if(specSkill != null){
//            skillFilter= skillRepository.findAll(specSkill);
//            if(skillFilter.isEmpty()){
//                return null;
//            }
//        }
//        if(specTeammate != null) {
//            specTeammate = specTeammate.and(
//                    (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("Id"), resource.getTeammateId()));
//        }else{
//            specTeammate = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("Id"), resource.getTeammateId());
//        }
//        Iterable<Teammate> teammates = teammateService.findAll(specTeammate);
//        if(!teammates.iterator().hasNext()){
//            return null;
//        }
//        Teammate teammate = teammates.iterator().next();
//        for(Skill skill: skillFilter){
//            if(!teammate.getSkills().contains(skill)){
//                return null;
//            }
//        }
//        resourceDTO.setJobTitle(teammate.getJobTitle());
//        resourceDTO.setRank(teammate.getRank());
//        resourceDTO.setSkills(teammate.getSkills().stream().map(Skill::getSkill).collect(Collectors.toSet()));
//        return resourceDTO;
//    }
//
//    public ResourceDtoOnRequest getResourceDTOEntity(Resource resource) {
//        ModelMapper modelMapper = new ModelMapper();
//        ResourceDtoOnRequest resourceDTO = modelMapper.map(resource, ResourceDtoOnRequest.class);
//        Optional<Teammate> teammateOpt = teammateService.findById(resource.getTeammateId());
//        if (teammateOpt.isPresent()) {
//            Teammate teammate = teammateOpt.get();
//            resourceDTO.setJobTitle(teammate.getJobTitle());
//            resourceDTO.setRank(teammate.getRank());
//            resourceDTO.setSkills(teammate.getSkills().stream().map(Skill::getSkill).collect(Collectors.toSet()));
//        }
//        return resourceDTO;
//    }
//}
