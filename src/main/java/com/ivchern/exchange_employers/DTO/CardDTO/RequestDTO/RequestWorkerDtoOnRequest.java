package com.ivchern.exchange_employers.DTO.CardDTO.RequestDTO;

import com.ivchern.exchange_employers.Model.Status;
import com.ivchern.exchange_employers.Model.Team.Skill;
import com.ivchern.exchange_employers.Model.User.OwnerDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    @Schema(description = "Требуется до", example = "2024-01-01", type = "string")
    private Date needBefore;
    boolean isInterviewNeeded;
    private OwnerDetails ownerDetail;
    private Set<Skill> skills;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Status status;
}

