package com.ivchern.exchange_employers.Repositories;

import com.ivchern.exchange_employers.Model.User.ERole;
import com.ivchern.exchange_employers.Model.User.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}