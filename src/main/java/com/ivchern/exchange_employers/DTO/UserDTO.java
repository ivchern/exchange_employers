package com.ivchern.exchange_employers.DTO;

import com.ivchern.exchange_employers.Model.User.Contact;
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
    private String nameTeam;
    private String teamDescription;
    private List<ContactDTO> contacts;

}
