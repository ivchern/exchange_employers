package com.ivchern.exchange_employers.Model.Team;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Table(name = "skill")

public class Skill {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "skill_name")
    private String skill;
    @Column(name = "description")
    private String description;
}
