package com.ivchern.exchange_employers.Model.Card;

import com.ivchern.exchange_employers.Model.BaseEntity;
import com.ivchern.exchange_employers.Model.Team.Skill;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class RequestWorker extends BaseEntity {
    @Column(name = "job_title")
    private String jobTitle;
    @Column(name = "project")
    private String projectName;
    @Column(name = "rank")
    private Rank rank;
    @Column(name = "description")
    @Size(max = 1024)
    private String description;
    @Column(name = "location_worked")
    private String locationWorked;
    @Column(name = "start_date")
    private Date needBefore;
    @Column(name = "interview_customer")
    private boolean isInterviewNeeded;
    @ManyToMany
    @Column(name = "skills")
    @JoinTable(name = "request_skills",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<Skill> skills;
}
