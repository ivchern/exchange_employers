package com.ivchern.exchange_employers.Repositories;

import com.ivchern.exchange_employers.Model.User.Contact;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends CrudRepository<Contact, Long> {
    @Query(value = "SELECT * FROM contact WHERE user_id = ? AND type_contact = ?", nativeQuery = true)
    Optional<Contact> findByUserIdAndTypeContact(Long id, String typeContact);

    List<Contact> findAllByUserId(Long id);
}
