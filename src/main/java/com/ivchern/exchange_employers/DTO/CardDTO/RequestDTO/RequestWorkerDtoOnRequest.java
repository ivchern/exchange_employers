package com.ivchern.exchange_employers.DTO.CardDTO.RequestDTO;

import com.ivchern.exchange_employers.Model.Team.Skill;
import com.ivchern.exchange_employers.Model.User.OwnerDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestWorkerDtoOnRequest{
    private Long id;
    private String jobTitle;
    private String projectName;
    private String rank;
    private String description;
    private String locationWorked;
    private Date needBefore;
    boolean isInterviewNeeded;
    private Long ownerId;
    private OwnerDetails ownerDetail;
    private Set<Skill> skills;
}
