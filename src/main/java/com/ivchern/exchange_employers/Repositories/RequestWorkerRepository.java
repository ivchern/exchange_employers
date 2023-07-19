package com.ivchern.exchange_employers.Repositories;

import com.ivchern.exchange_employers.Model.Card.RequestWorker;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestWorkerRepository extends CrudRepository<RequestWorker, Long>, JpaSpecificationExecutor<RequestWorker> {
    @Override
    @NotNull
    List<RequestWorker> findAll();

    @NotNull
    List<RequestWorker> findAll(Pageable pageable);

}
