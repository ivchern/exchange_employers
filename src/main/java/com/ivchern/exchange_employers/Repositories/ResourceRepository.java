package com.ivchern.exchange_employers.Repositories;

import com.ivchern.exchange_employers.Model.Card.Resource;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ResourceRepository extends CrudRepository<Resource,Long>, JpaSpecificationExecutor<Resource> {
    
}
