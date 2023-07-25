package com.ivchern.exchange_employers.DTO.CardDTO.ResourceDTO;

import com.ivchern.exchange_employers.Model.Status;
import com.ivchern.exchange_employers.Model.Team.Skill;
import com.ivchern.exchange_employers.Model.User.OwnerDetails;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceDtoOnRequest extends ResourceDtoBase{
    private Long id;
    private String jobTitleResource;
    private String rank;
    private Set<Skill> skills;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Status status;
    private OwnerDetails ownerDetail;
}
