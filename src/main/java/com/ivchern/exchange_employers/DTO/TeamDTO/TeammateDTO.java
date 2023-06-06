package com.ivchern.exchange_employers.DTO.TeamDTO;

import com.ivchern.exchange_employers.Model.Team.Skill;
import jakarta.persistence.Column;
import jakarta.persistence.JoinTable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class TeammateDTO {
//    private Long id;
    private String firstname;
    private String lastname;
    private String jobTitle;
    private String rank;
    private Long teamId;
    private Long ownerId;
    private Set<String> skills;

}
