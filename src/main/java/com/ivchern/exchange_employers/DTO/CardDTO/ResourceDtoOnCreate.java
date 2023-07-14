package com.ivchern.exchange_employers.DTO.CardDTO;

import com.ivchern.exchange_employers.DTO.TeamDTO.TeammateCardDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceDtoOnCreate extends TeammateCardDTO {
    private String description;
    private String locationWorked;
    private Date fromFree;
    private Date endFree;
    private Long teammateId;
    private Long ownerId;
}
