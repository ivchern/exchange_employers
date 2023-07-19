package com.ivchern.exchange_employers.Model.Card;

import com.fasterxml.jackson.annotation.*;
import com.ivchern.exchange_employers.Model.BaseEntity;
import com.ivchern.exchange_employers.Model.Team.Skill;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RequestWorker extends BaseEntity {
    @Column(name = "job_title")
    private String jobTitle;
    @Column(name = "project")
    private String projectName;
    @Column(name = "rank")
    private String rank;
    @Column(name = "description")
    @Size(max = 1024)
    private String description;
    @Column(name = "location_worked")
    private String locationWorked;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "start_date")
    private Date needBefore;
    @Column(name = "interview_customer")
    private boolean isInterviewNeeded;
    @Column(name = "owner_id")
    private Long ownerId;
    @ManyToMany
    @Column(name = "skills")
    @JoinTable(name = "request_skills",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<Skill> skills;
}
