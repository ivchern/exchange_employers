package com.ivchern.exchange_employers.Model.Team;

import com.ivchern.exchange_employers.Model.BaseEntity;
import com.ivchern.exchange_employers.Model.Card.Rank;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Table(name = "teammate")
public class
Teammate extends BaseEntity {
    private String firstname;
    private String lastname;
    @Column(name = "job_title")
    private String jobTitle;
    @Column(name = "rank")
    @Length(max = 20)
    private String rank;
    @Column(name = "team_id")
    private Long teamId;
    @Column(name = "owner_id")
    private Long ownerId;
    @Column(name = "skills")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "teammate_skill",
                    joinColumns = {@JoinColumn(name = "teammate_id")},
                    inverseJoinColumns = {@JoinColumn(name = "skill_id")})
    private Set<Skill> skills;
}
