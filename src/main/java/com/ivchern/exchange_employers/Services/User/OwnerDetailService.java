package com.ivchern.exchange_employers.Services.User;

import com.ivchern.exchange_employers.Model.User.OwnerDetails;

import java.util.Optional;

public interface OwnerDetailService {
    Optional<OwnerDetails> findByOwnerId(Long ownerId);
}
