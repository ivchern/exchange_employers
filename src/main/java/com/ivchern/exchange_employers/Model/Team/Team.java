package com.ivchern.exchange_employers.Model.Team;

import com.ivchern.exchange_employers.Model.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Entity
public class Team extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    @Length(max = 255)
    private String description;
    @Column(name = "owner_id")
    private Long ownerId;

}
