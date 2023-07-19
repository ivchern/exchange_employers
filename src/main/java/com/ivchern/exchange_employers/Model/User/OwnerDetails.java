package com.ivchern.exchange_employers.Model.User;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ivchern.exchange_employers.Model.Card.RequestWorker;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "user_details")
@Data
public class OwnerDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;
    @Size(max = 100)
    @Column(name = "lastname")
    private String lastname;
    @Size(max = 100)
    @Column(name = "firstname")
    private String firstname;
    @ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinTable(	name = "user_contacts",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id"))
    private Set<Contact> contacts = new HashSet<>();
//    @JsonManagedReference(value = "owner_id")
//    public List<RequestWorker> requestWorkers;

}
