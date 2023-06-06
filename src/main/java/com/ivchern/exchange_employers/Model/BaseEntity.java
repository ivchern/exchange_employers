package com.ivchern.exchange_employers.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;
import java.util.Date;

@MappedSuperclass
@Data
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @CreatedDate
    @Column(name = "created")
    private LocalDateTime created;

    @LastModifiedBy
    @Column(name = "updated")
    private LocalDateTime updated;
    @Enumerated(EnumType.STRING)
    private Status status;
}
