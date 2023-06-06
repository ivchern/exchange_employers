package com.ivchern.exchange_employers.Model.Card;

import com.ivchern.exchange_employers.Model.BaseEntity;
import com.ivchern.exchange_employers.Model.Team.Skill;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class Resource extends BaseEntity {
    @Column(name = "description")
    @Size(max = 1024)
    private String description;
    @Column(name = "location_worked")
    private String locationWorked;
    @Column(name = "from_free")
    private Date fromFree;
    @Column(name = "end_free")
    private Date endFree;
    @Column(name = "teammate_id")
    private Long teammateId;
}

