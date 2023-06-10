package com.ivchern.exchange_employers.DTO.UserDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String firstname;
    private String email;
    private String lastname;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long teamId;
    private String nameTeam;
    private String teamDescription;
    private List<ContactDTO> contacts;

}
