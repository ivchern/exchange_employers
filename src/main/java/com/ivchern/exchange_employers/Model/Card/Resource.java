package com.ivchern.exchange_employers.Model.Card;

import com.ivchern.exchange_employers.Model.BaseEntity;
import com.ivchern.exchange_employers.Model.Team.Teammate;
import com.ivchern.exchange_employers.Model.User.OwnerDetails;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Resource extends BaseEntity {
    @Column(name = "card_title")
    private String cardTitle;
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
    @ManyToOne()
    @JoinTable(name = "teammate_resource",
                joinColumns = @JoinColumn( name = "resource_id"),
                inverseJoinColumns = @JoinColumn( name = "teammate_id"))
    private Teammate teammate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private OwnerDetails ownerDetails;
}

