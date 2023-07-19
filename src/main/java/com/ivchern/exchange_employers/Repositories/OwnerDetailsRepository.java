package com.ivchern.exchange_employers.Repositories;

import com.ivchern.exchange_employers.Model.User.OwnerDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerDetailsRepository extends CrudRepository<OwnerDetails, Long> {
    Optional<OwnerDetails> findById(Long ownerId);
}