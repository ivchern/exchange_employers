package com.ivchern.exchange_employers.Services.Card.Resource;

import com.ivchern.exchange_employers.DTO.CardDTO.ResourceDTO.ResourceDtoOnRequest;
import com.ivchern.exchange_employers.Model.Card.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public final class ResourceMapper {
    static List<ResourceDtoOnRequest> mapEntitiesIntoDTOs(Iterable<Resource> entities) {
        List<ResourceDtoOnRequest> dtos = new ArrayList<>();

        entities.forEach(e -> dtos.add(mapEntityIntoDTO(e)));

        return dtos;
    }

    static ResourceDtoOnRequest mapEntityIntoDTO(Resource resource) {
        ResourceDtoOnRequest dto = new ResourceDtoOnRequest();
        dto.setId(resource.getId());
        dto.setCardTitle(resource.getCardTitle());
        dto.setDescription(resource.getDescription());
        dto.setLocationWorked(resource.getLocationWorked());
        dto.setFromFree(resource.getFromFree());
        dto.setEndFree(resource.getEndFree());
        dto.setJobTitleResource(resource.getTeammate().getJobTitle());
        dto.setRank(resource.getTeammate().getRank());
        dto.setSkills(resource.getTeammate().getSkills());
        dto.setStatus(resource.getStatus());
        dto.setCreated(resource.getCreated());
        dto.setUpdated(resource.getUpdated());
        dto.setOwnerDetail(resource.getOwnerDetails());
        return dto;
    }

    static Page<ResourceDtoOnRequest> mapEntityPageIntoDTOPage(Pageable pageResource, Page<Resource> source) {
        List<ResourceDtoOnRequest> dtos = mapEntitiesIntoDTOs(source.getContent());
        return new PageImpl<>(dtos, pageResource, source.getTotalElements());
    }
}
