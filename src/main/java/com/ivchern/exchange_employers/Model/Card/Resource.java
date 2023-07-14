package com.ivchern.exchange_employers.Model.Card;

import com.ivchern.exchange_employers.Model.BaseEntity;
import com.ivchern.exchange_employers.Model.Team.Skill;
import com.ivchern.exchange_employers.Model.User.OwnerDetail;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Resource extends BaseEntity {
    @Column(name = "description")
    @Size(max = 1024)
    private String description;
    @Column(name = "location_worked")
    private String locationWorked;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    @Column(name = "from_free")
    private Date fromFree;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    @Column(name = "end_free")
    private Date endFree;
    @Column(name = "teammate_id")
    private Long teammateId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private OwnerDetail ownerDetail;
}

