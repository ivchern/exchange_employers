package com.ivchern.exchange_employers.Services.Card.Resource;

import com.ivchern.exchange_employers.DTO.CardDTO.ResourceDTO.ResourceDtoOnRequest;
import com.ivchern.exchange_employers.DTO.CardDTO.ResourceDTO.ResourceDtoOnSave;
import com.ivchern.exchange_employers.Model.Card.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.security.Principal;

public interface ResourceService {
    Resource update(ResourceDtoOnSave resourceDTO, Long Id, Principal principal);
    Page<ResourceDtoOnRequest> findAll(Specification<Resource> SpecRequest, Pageable page);
    Resource save(ResourceDtoOnSave resourceDtoOnSave);
    void delete(Long id, Principal principal);


}
