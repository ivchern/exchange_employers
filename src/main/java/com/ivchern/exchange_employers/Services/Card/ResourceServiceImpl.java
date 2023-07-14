package com.ivchern.exchange_employers.Services.Card;

import com.ivchern.exchange_employers.DTO.CardDTO.ResourceDtoOnCreate;
import com.ivchern.exchange_employers.DTO.CardDTO.ResourceDtoOnRequest;
import com.ivchern.exchange_employers.Model.Card.Resource;
import com.ivchern.exchange_employers.Model.Status;
import com.ivchern.exchange_employers.Model.Team.Skill;
import com.ivchern.exchange_employers.Model.Team.Teammate;
import com.ivchern.exchange_employers.Model.User.OwnerDetail;
import com.ivchern.exchange_employers.Repositories.ResourceRepository;
import com.ivchern.exchange_employers.Repositories.SkillRepository;
import com.ivchern.exchange_employers.Repositories.UserDetailsRepository;
import com.ivchern.exchange_employers.Services.Teammate.TeammateService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ResourceServiceImpl implements ResourceService {

    private TeammateService teammateService;
    private ResourceRepository resourceRepository;
    private SkillRepository skillRepository;
    private UserDetailsRepository userDetailsRepository;

    public ResourceServiceImpl(TeammateService teammateService, ResourceRepository resourceRepository, SkillRepository skillRepository, UserDetailsRepository userDetailsRepository) {
        this.teammateService = teammateService;
        this.resourceRepository = resourceRepository;
        this.skillRepository = skillRepository;
        this.userDetailsRepository = userDetailsRepository;
    }


    @Override
    public ResourceDtoOnRequest save(ResourceDtoOnCreate resource) {
        Resource resourceSave = new Resource();
        resourceSave.setTeammateId(resource.getTeammateId());
        resourceSave.setDescription(resource.getDescription());
        resourceSave.setLocationWorked(resource.getLocationWorked());
        resourceSave.setFromFree(resource.getFromFree());
        resourceSave.setEndFree(resource.getEndFree());

        resourceSave.setStatus(Status.ACTIVE);
        resourceSave.setCreated(LocalDateTime.now());
        resourceSave.setUpdated(LocalDateTime.now());

        var userDetailOpt = userDetailsRepository.findById(resource.getOwnerId());
        OwnerDetail ownerDetail = userDetailOpt.get();
        resourceSave.setOwnerDetail(ownerDetail);

        Resource resourceUpd = resourceRepository.save(resourceSave);
        return getResourceDTOEntity(resourceUpd);
    }

    @Override
    public ResourceDtoOnRequest update(ResourceDtoOnRequest resourceDTO, Long Id) {
        resourceDTO.setUpdated(LocalDateTime.now());

        ModelMapper modelMapper = new ModelMapper();
        Resource resource = modelMapper.map(resourceDTO, Resource.class);
        resource.setUpdated(LocalDateTime.now());
        Resource resourceUpd = resourceRepository.save(resource);
        return getResourceDTOEntity(resourceUpd);
    }

    @Override
    public List<ResourceDtoOnRequest> search(Specification<Resource> specResources, Specification<Skill> specSkill, Specification<Teammate> specTeammate, Pageable page) {
        List<ResourceDtoOnRequest> resourceDTO =  new ArrayList<>();

        Iterable<Resource> resource = resourceRepository.findAll(specResources, page);

        resource.forEach(resource1 -> {
            ResourceDtoOnRequest resourceWithTeammate = getResourceDTOEntity(resource1, specSkill, specTeammate);
            if(resourceWithTeammate != null){
                resourceDTO.add(resourceWithTeammate);
            }
        });
        return resourceDTO;
    }

    @Override
    public List<ResourceDtoOnRequest> findAll() {
        List<ResourceDtoOnRequest> resourceDTO =  new ArrayList<>();

        Iterable<Resource> resource = resourceRepository.findAll();

        resource.forEach(resource1 -> {
            resourceDTO.add(getResourceDTOEntity(resource1));
        });
        return resourceDTO;
    }

    @Override
    public Optional<ResourceDtoOnRequest> findById(Long id) {
        ResourceDtoOnRequest resourceDTO;
        Optional<Resource> resourceOpt = resourceRepository.findById(id);
        if (resourceOpt.isPresent()){
            resourceDTO = getResourceDTOEntity(resourceOpt.get());
            Optional<ResourceDtoOnRequest> res = Optional.of(resourceDTO);
            return res;
        }else{
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        Optional<Resource> resourceOpt = resourceRepository.findById(id);
        if (resourceOpt.isPresent()){
            Resource resource = resourceOpt.get();
            resource.setStatus(Status.DELETED);
        }
    }

    public ResourceDtoOnRequest getResourceDTOEntity(Resource resource,
                                                     Specification<Skill> specSkill,Specification<Teammate> specTeammate) {
        ModelMapper modelMapper = new ModelMapper();
        ResourceDtoOnRequest resourceDTO = modelMapper.map(resource, ResourceDtoOnRequest.class);
        List<Skill> skillFilter = new ArrayList<>();
        if(specSkill != null){
            skillFilter= skillRepository.findAll(specSkill);
            if(skillFilter.isEmpty()){
                return null;
            }
        }
        if(specTeammate != null) {
            specTeammate = specTeammate.and(
                    (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("Id"), resource.getTeammateId()));
        }else{
            specTeammate = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("Id"), resource.getTeammateId());
        }
        Iterable<Teammate> teammates = teammateService.findAll(specTeammate);
        if(!teammates.iterator().hasNext()){
            return null;
        }
        Teammate teammate = teammates.iterator().next();
        for(Skill skill: skillFilter){
            if(!teammate.getSkills().contains(skill)){
                return null;
            }
        }
        resourceDTO.setJobTitle(teammate.getJobTitle());
        resourceDTO.setRank(teammate.getRank());
        resourceDTO.setSkills(teammate.getSkills().stream().map(Skill::getSkill).collect(Collectors.toSet()));
        return resourceDTO;
    }

    public ResourceDtoOnRequest getResourceDTOEntity(Resource resource) {
        ModelMapper modelMapper = new ModelMapper();
        ResourceDtoOnRequest resourceDTO = modelMapper.map(resource, ResourceDtoOnRequest.class);
        Optional<Teammate> teammateOpt = teammateService.findById(resource.getTeammateId());
        if (teammateOpt.isPresent()) {
            Teammate teammate = teammateOpt.get();
            resourceDTO.setJobTitle(teammate.getJobTitle());
            resourceDTO.setRank(teammate.getRank());
            resourceDTO.setSkills(teammate.getSkills().stream().map(Skill::getSkill).collect(Collectors.toSet()));
        }
        return resourceDTO;
    }
}
