package com.ivchern.exchange_employers.Services.User;

import com.ivchern.exchange_employers.Model.User.OwnerDetails;
import com.ivchern.exchange_employers.Repositories.OwnerDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OwnerDetailServiceImpl implements OwnerDetailService{

    final private OwnerDetailsRepository ownerDetailsRepository;

    public OwnerDetailServiceImpl(OwnerDetailsRepository ownerDetailsRepository) {
        this.ownerDetailsRepository = ownerDetailsRepository;
    }
    @Override
    public Optional<OwnerDetails> findByOwnerId(Long ownerId){
        return ownerDetailsRepository.findById(ownerId);
    }
}
