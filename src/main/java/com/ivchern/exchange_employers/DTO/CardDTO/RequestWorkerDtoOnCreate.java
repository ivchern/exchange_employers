package com.ivchern.exchange_employers.DTO.CardDTO;

import com.ivchern.exchange_employers.Model.Card.Rank;
import com.ivchern.exchange_employers.Model.Team.Skill;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.NONE, force = true)
public class RequestWorkerDtoOnCreate {
    private String jobTitle;
    private String projectName;
    private String rank;
    private String description;
    private String locationWorked;
    private Date needBefore;
    private boolean isInterviewNeeded;
    private Set<String> skills;
}
