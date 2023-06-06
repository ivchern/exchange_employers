package com.ivchern.exchange_employers.Services.Card;

import com.ivchern.exchange_employers.DTO.CardDTO.ResourceDtoOnCreate;
import com.ivchern.exchange_employers.DTO.CardDTO.ResourceDtoOnRequest;

import java.util.List;
import java.util.Optional;

public interface ResourceService {
    ResourceDtoOnRequest save(ResourceDtoOnCreate resource);
    ResourceDtoOnRequest update(ResourceDtoOnRequest resourceDTO, Long Id);
    List<ResourceDtoOnRequest> findAll();
    Optional<ResourceDtoOnRequest> findById(Long Id);
    void delete(Long id);


}
