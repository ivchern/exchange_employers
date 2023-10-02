package com.ivchern.exchange_employers.Services.Card.RequestWorker;

import com.ivchern.exchange_employers.DTO.CardDTO.RequestDTO.RequestWorkerDtoOnSave;
import com.ivchern.exchange_employers.DTO.CardDTO.RequestDTO.RequestWorkerDtoOnRequest;
import com.ivchern.exchange_employers.Model.Card.RequestWorker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.security.Principal;
import java.util.List;

public interface RequestWorkerService {
    RequestWorkerDtoOnRequest save(RequestWorkerDtoOnSave resource);
    RequestWorkerDtoOnRequest update(Long id, RequestWorkerDtoOnSave requestWorker, Principal principal);
    RequestWorkerDtoOnRequest findById(Long id);
    void delete(Long id, Principal principal);
    Page<RequestWorkerDtoOnRequest> findAll(Specification<RequestWorker> specRequest, Pageable paging);
    List<RequestWorker> getRecommendationById(Long id);
}
