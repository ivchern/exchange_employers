package com.ivchern.exchange_employers.Services.User;

import com.ivchern.exchange_employers.DTO.UserDTO.ContactDTO;
import com.ivchern.exchange_employers.DTO.UserDTO.UserDTO;
import com.ivchern.exchange_employers.Model.Team.Team;
import com.ivchern.exchange_employers.Model.User.Contact;
import com.ivchern.exchange_employers.Model.User.User;
import com.ivchern.exchange_employers.Repositories.ContactRepository;
import com.ivchern.exchange_employers.Repositories.TeamRepository;
import com.ivchern.exchange_employers.Repositories.UserRepository;
import com.ivchern.exchange_employers.Services.Team.TeamServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//@ExtendWith(MockitoExtension.class)
//public class UserServiceTest {
//    @Mock
//    UserRepository userRepository;
//    @Mock
//    TeamRepository teamRepository;
//    @Mock
//    ContactRepository contactRepository;
//    @Mock
//    TeamServiceImpl teamService;
//    @InjectMocks
//    UserServiceImpl userService;
//
//    @Test
//    @MockitoSettings(strictness = Strictness.LENIENT)
//    public void shouldUpdateUser(){
//        Long id = 1L;
//        List<Contact> contacts = getContact();
//        User user = getUser();
//        Team team = getTeam();
//
//        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
//        Mockito.when(teamRepository.findByName(user.getUsername())).thenReturn(Optional.of(team));
//        Mockito.when(contactRepository.findAllByUserId(id)).thenReturn(contacts);
//        Mockito.when(teamRepository.findByOwnerId(id)).thenReturn(Optional.of(team));
//        Mockito.when(teamRepository.findById(id)).thenReturn(Optional.of(team));
//        Mockito.when(teamRepository.getNameById(id)).thenReturn("Javascript team");
//        Mockito.when(teamRepository.getDescriptionById(id)).thenReturn("Team dev on js");
//
//        ContactDTO contactsUserDto= new ContactDTO("Telegram", "@Morosi");
//        UserDTO updatedUser = new UserDTO();
//        updatedUser.setId(id);
//        updatedUser.setLastname("Mohrin");
//        updatedUser.setEmail("mohrin@test.com");
//        updatedUser.setContacts(List.of(contactsUserDto));
//        updatedUser.setTeamId(id);
//        updatedUser.setFirstname(user.getFirstname());
////        updatedUser.setNameTeam("Javascript team");
////        updatedUser.setTeamDescription("Team dev on js");
//
//        UserDTO userDTO = userService.update(updatedUser, id);
//
//        Assertions.assertNotNull(userDTO);
//        Assertions.assertEquals(updatedUser.getUsername(), userDTO.getUsername());
//        Assertions.assertEquals(updatedUser.getContacts(), userDTO.getContacts());
//        Assertions.assertEquals(updatedUser.getLastname(), userDTO.getLastname());
//        Assertions.assertEquals(updatedUser.getNameTeam() , userDTO.getNameTeam());
//        Assertions.assertEquals(updatedUser.getTeamDescription() , userDTO.getTeamDescription());
//        Assertions.assertEquals(updatedUser.getEmail() , userDTO.getEmail());
//    }
//
//    private User getUser(){
//        User user = new User();
//        user.setUsername("test1234");
//        user.setPassword("1234567");
//        user.setEmail("testIlya@test.com");
//        user.setFirstname("Ilya");
//        user.setLastname("Ilyushin");
//        return user;
//    }
//
//    private Team getTeam(){
//        Team team = new Team();
//        team.setId(1L);
//        team.setName("Team lead Team");
//        team.setDescription("Command Team leads");
//        team.setOwnerId(1L);
//        team.setCreated(LocalDateTime.now());
//        team.setUpdated(LocalDateTime.now());
//        return team;
//    }
//
//    private List<Contact> getContact(){
//        Contact contact1 = new Contact();
//        Contact contact2 = new Contact();
//        Contact contact3 = new Contact();
//
//        contact1.setId(1L);
//        contact1.setUserId(1L);
//        contact1.setTypeContact("Telegram");
//        contact1.setContact("@Ilusha11");
//
//        contact2.setId(2L);
//        contact2.setUserId(1L);
//        contact2.setTypeContact("WhatsApp");
//        contact2.setContact("@Ilusha112345");
//
//        contact3.setId(3L);
//        contact3.setUserId(1L);
//        contact3.setTypeContact("Home Mail");
//        contact3.setContact("@IluhaPus211@test.net");
//        return List.of(contact1, contact2, contact3);
//    }
//}
