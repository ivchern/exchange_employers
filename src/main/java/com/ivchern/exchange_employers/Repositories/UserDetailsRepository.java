package com.ivchern.exchange_employers.Repositories;

import com.ivchern.exchange_employers.Model.User.OwnerDetail;
import org.springframework.data.repository.CrudRepository;

public interface UserDetailsRepository extends CrudRepository<OwnerDetail, Long> {
}