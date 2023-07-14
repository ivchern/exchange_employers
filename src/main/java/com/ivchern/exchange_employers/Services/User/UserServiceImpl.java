package com.ivchern.exchange_employers.Services.User;

import com.ivchern.exchange_employers.DTO.TeamDTO.TeamDTO;
import com.ivchern.exchange_employers.DTO.UserDTO.UserDTO;
import com.ivchern.exchange_employers.DTO.UserDTO.ContactDTO;
import com.ivchern.exchange_employers.Model.Team.Team;
import com.ivchern.exchange_employers.Model.User.Contact;
import com.ivchern.exchange_employers.Model.User.User;
import com.ivchern.exchange_employers.Repositories.ContactRepository;
import com.ivchern.exchange_employers.Repositories.UserDetailsRepository;
import com.ivchern.exchange_employers.Repositories.UserRepository;
import com.ivchern.exchange_employers.Services.Team.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private ContactRepository contactRepository;
    private UserRepository userRepository;
    private TeamService teamService;
    private UserDetailsRepository userDetailsRepository;

    public UserServiceImpl(ContactRepository contactRepository, UserRepository userRepository, TeamService teamService, UserDetailsRepository userDetailsRepository) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
        this.teamService = teamService;
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public UserDTO update(UserDTO userDTO, Long id) {
        contactUserSave(userDTO.getContacts(), id);
        TeamDTO teamDTO = new TeamDTO(userDTO.getNameTeam(), userDTO.getTeamDescription());
        if(teamService.existByOwnerId(id) == true){
            teamDTO = teamService.update(teamDTO, id);
        }else{
            teamDTO = teamService.save(teamDTO, id);
        }
        if(teamDTO.getName() != null){
            userDTO.setNameTeam(teamDTO.getName());;
        }
        if(teamDTO.getDescription() != null){
            userDTO.setTeamDescription(teamDTO.getDescription());
        }
        userDTO.setTeamId(id);

        if(userDTO.getFirstname() != null) {
            userRepository.saveFirstnameById(id, userDTO.getFirstname());
        }else{
            userDTO.setFirstname(userRepository.findFirstnameById(id));
        }

        if(userDTO.getLastname() != null) {
            userRepository.saveLastnameById(id, userDTO.getLastname());
        }else{
            userDTO.setLastname(userRepository.findLastnameById(id));
        }
        log.info(String.valueOf(userDTO));
        return userDTO;
    }

    @Override
    public Optional<UserDTO> findById(Long id) {
        Optional<UserDTO> userDTOOpt;
        Team team;
        Optional<User> userOpt = userRepository.findById(id);
        if(!userOpt.isPresent()){
            userDTOOpt = Optional.empty();
            return userDTOOpt;
        }
        User user = userOpt.get();
        Optional<Team> teamOpt = teamService.findById(user.getId());
        if(teamOpt.isPresent()){
            team = teamOpt.get();
        }
        if(teamOpt.isPresent()){
            team = teamOpt.get();
        }else {
            team = new Team();
        }
        List<Contact> contact = contactRepository.findAllByUserId(id);
        ModelMapper modelMapper = new ModelMapper();

        var firstname = user.getOwnerDetail().getFirstname();
        var lastname = user.getOwnerDetail().getLastname();

        List<ContactDTO> userContacts = modelMapper.map(contact, new TypeToken<List<ContactDTO>>(){}.getType());

        userDTOOpt = Optional.ofNullable(new UserDTO(user.getId(), user.getUsername(), firstname, user.getEmail(),
                    lastname, team.getId(), team.getName(), team.getDescription(), userContacts));

        return userDTOOpt;
    }

    public Optional<UserDTO> findByUsername(String username){
        Optional<UserDTO> userDTOOpt;

        Optional<User> userOpt = userRepository.findByUsername(username);
        if(!userOpt.isPresent()){
            userDTOOpt = Optional.empty();
            return userDTOOpt;
        }
        User user = userOpt.get();
        Team team;
        Optional<Team> teamOpt = teamService.findById(user.getId());
        if (teamOpt.isPresent()) {
            team = teamOpt.get();
        }else{
            team = null;
        }
        List<Contact> contact = contactRepository.findAllByUserId(user.getId());
        ModelMapper modelMapper = new ModelMapper();

        List<ContactDTO> userContacts = modelMapper.map(contact, new TypeToken<List<ContactDTO>>(){}.getType());

        var firstname = user.getOwnerDetail().getFirstname();
        var lastname = user.getOwnerDetail().getLastname();

        if(team != null){
            userDTOOpt = Optional.ofNullable(new UserDTO(user.getId(), user.getUsername(), firstname, user.getEmail(),
                    lastname, team.getId(),team.getName(), team.getDescription(), userContacts));

        }else{
            userDTOOpt = Optional.ofNullable(new UserDTO(user.getId(), user.getUsername(), firstname, user.getEmail(),
                    lastname, null, null, null, null));

        }

        return userDTOOpt;
    }

    private void contactUserSave(List<ContactDTO> contactDTO, Long id){
        ModelMapper modelMapper = new ModelMapper();
        List<Contact> userContacts = modelMapper.map(contactDTO, new TypeToken<List<Contact>>(){}.getType());

        userContacts.forEach(contact -> {
            contact.setUserId(id);
            contactRepository.findByUserIdAndTypeContact(id, contact.getTypeContact()).
                    ifPresent(contact1 -> contact.setId(contact1.getId()));
        });
        var userDetailsOpt = userDetailsRepository.findById(id);
        var userDetails = userDetailsOpt.get();
        userDetails.setContacts(new HashSet<>(userContacts));
        userDetailsRepository.save(userDetails);

//        log.info(userContacts.toString());
//        contactRepository.saveAll(userContacts);
    }
}
