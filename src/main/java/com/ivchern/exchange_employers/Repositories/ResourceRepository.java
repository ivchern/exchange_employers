package com.ivchern.exchange_employers.Repositories;

import com.ivchern.exchange_employers.Model.Card.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends CrudRepository<Resource,Long>, JpaSpecificationExecutor<Resource> {
    @Override
    @NotNull
    List<Resource> findAll();
}
