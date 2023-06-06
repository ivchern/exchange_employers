package com.ivchern.exchange_employers.Services.Card;

import com.ivchern.exchange_employers.DTO.CardDTO.RequestWorkerDtoOnCreate;
import com.ivchern.exchange_employers.Model.Card.RequestWorker;

import java.util.Optional;

public interface RequestWorkerService {
    Iterable<RequestWorker> findAll();
    RequestWorker save(RequestWorkerDtoOnCreate resource);
    RequestWorker update(RequestWorker requestWorker);
    Optional<RequestWorker> findById(Long id);
    void delete(Long id);

}
