package com.ivchern.exchange_employers.Repositories;

import com.ivchern.exchange_employers.Model.User.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "update users set firstname = ?2 where id = ?1", nativeQuery = true)
    int saveFirstnameById(Long id, String firstname);
    @Modifying
    @Transactional
    @Query(value = "update users set lastname = ?2 where id = ?1", nativeQuery = true)
    int saveLastnameById(Long id, String lastname);
}