package com.ivchern.exchange_employers.Services.Card.RequestWorker;

import com.ivchern.exchange_employers.DTO.CardDTO.RequestDTO.RequestWorkerDtoOnRequest;
import com.ivchern.exchange_employers.Model.Card.RequestWorker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public final class RequestWorkerMapper {
    static List<RequestWorkerDtoOnRequest> mapEntitiesIntoDTOs(Iterable<RequestWorker> entities) {
        List<RequestWorkerDtoOnRequest> dtos = new ArrayList<>();
        entities.forEach(e -> dtos.add(mapEntityIntoDTO(e)));
        return dtos;
    }

    static RequestWorkerDtoOnRequest mapEntityIntoDTO(RequestWorker request) {
        RequestWorkerDtoOnRequest dto = new RequestWorkerDtoOnRequest();
        dto.setId(request.getId());
        dto.setJobTitle(request.getJobTitle());
        dto.setProjectName(request.getProjectName());
        dto.setRank(request.getRank());
        dto.setDescription(request.getDescription());
        dto.setLocationWorked(request.getLocationWorked());
        dto.setNeedBefore(request.getNeedBefore());
        dto.setInterviewNeeded(request.isInterviewNeeded());
        dto.setOwnerDetail(request.getOwnerDetails());
        dto.setSkills(request.getSkills());
        dto.setCreated(request.getCreated());
        dto.setUpdated(request.getUpdated());
        dto.setStatus(request.getStatus());
        return dto;
    }

    static Page<RequestWorkerDtoOnRequest> mapEntityPageIntoDTOPage(Pageable pageRequestWorker, Page<RequestWorker> source) {
        List<RequestWorkerDtoOnRequest> dtos = mapEntitiesIntoDTOs(source.getContent());
        return new PageImpl<>(dtos, pageRequestWorker, source.getTotalElements());
    }
}