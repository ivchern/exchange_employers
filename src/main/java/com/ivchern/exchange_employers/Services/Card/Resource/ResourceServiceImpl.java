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
        resource.setFromFree(resourceDtoOnSave.getFromFree());

        if(resourceDtoOnSave.getEndFree() != null) {
            resource.setEndFree(resourceDtoOnSave.getEndFree());
        }

        if(resourceDtoOnSave.getTeammateId() != null){
            resource.setTeammate(teammateService.findById(resourceDtoOnSave.getTeammateId()).
                    orElseThrow( () -> new IllegalArgument("teammate not found with: " + resourceDtoOnSave.getTeammateId())));
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
    public ResourceDtoOnRequest findById(Long id) {
        var resource = resourceRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Card not found with id: " + id)
        );
        return ResourceMapper.mapEntityIntoDTO(resource);
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
