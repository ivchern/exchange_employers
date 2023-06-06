package com.ivchern.exchange_employers.Services.User;

import com.ivchern.exchange_employers.DTO.ContactDTO;
import com.ivchern.exchange_employers.DTO.TeamDTO.TeamDTO;
import com.ivchern.exchange_employers.DTO.UserDTO;
import com.ivchern.exchange_employers.Model.Status;
import com.ivchern.exchange_employers.Model.Team.Team;
import com.ivchern.exchange_employers.Model.User.Contact;
import com.ivchern.exchange_employers.Model.User.User;
import com.ivchern.exchange_employers.Repositories.ContactRepository;
import com.ivchern.exchange_employers.Repositories.TeamRepository;
import com.ivchern.exchange_employers.Repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private ContactRepository contactRepository;
    private TeamRepository teamRepository;
    private UserRepository userRepository;

    public UserServiceImpl(ContactRepository contactRepository, TeamRepository teamRepository, UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO update(UserDTO userDTO, Long id) {
        //TODO: add transsaction manager
        contactUserSave(userDTO.getContacts(), id);
        TeamDTO teamDTO = new TeamDTO(userDTO.getNameTeam(), userDTO.getTeamDescription());
        saveTeam(teamDTO, id);
        if(userDTO.getFirstname() != null) {
            userRepository.saveFirstnameById(id, userDTO.getFirstname());
        }
        if(userDTO.getLastname() != null) {
            userRepository.saveLastnameById(id, userDTO.getLastname());
        }
        return userDTO;
    }

    @Override
    public Optional<UserDTO> findById(Long id) {
        Optional<UserDTO> userDTOOpt;

        Optional<User> userOpt = userRepository.findById(id);
        if(!userOpt.isPresent()){
            userDTOOpt = Optional.empty();
            return userDTOOpt;
        }
        User user = userOpt.get();
        Team team = teamRepository.findByOwnerId(id).get();
        List<Contact> contact = contactRepository.findAllByUserId(id);
        ModelMapper modelMapper = new ModelMapper();

        List<ContactDTO> userContacts = modelMapper.map(contact, new TypeToken<List<ContactDTO>>(){}.getType());

        userDTOOpt = Optional.ofNullable(new UserDTO(user.getId(), user.getUsername(), user.getFirstname(), user.getEmail(),
                    user.getLastname(), team.getName(), team.getDescription(), userContacts));

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
        Optional<Team> teamOpt = teamRepository.findByOwnerId(user.getId());
        if (teamOpt.isPresent()) {
            team = teamOpt.get();
        }else{
            team = null;
        }
        List<Contact> contact = contactRepository.findAllByUserId(user.getId());
        ModelMapper modelMapper = new ModelMapper();

        List<ContactDTO> userContacts = modelMapper.map(contact, new TypeToken<List<ContactDTO>>(){}.getType());

        if(team != null){
            userDTOOpt = Optional.ofNullable(new UserDTO(user.getId(), user.getUsername(), user.getFirstname(), user.getEmail(),
                    user.getLastname(), team.getName(), team.getDescription(), userContacts));

        }else{
            userDTOOpt = Optional.ofNullable(new UserDTO(user.getId(), user.getUsername(), user.getFirstname(), user.getEmail(),
                    user.getLastname(), null, null, null));

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
        log.info(userContacts.toString());
        contactRepository.saveAll(userContacts);
    }

    private void saveTeam(TeamDTO teamDTO, Long id){
        ModelMapper modelMapper = new ModelMapper();
        Team team = modelMapper.map(teamDTO, Team.class);
        team.setOwnerId(id);
        team.setStatus(Status.ACTIVE);

        Optional<Team> ownerId = teamRepository.findByOwnerId(id);

        if(ownerId.isPresent()){
            team.setId(ownerId.get().getId());
        }else{
            team.setCreated(LocalDateTime.now());
        }
        team.setUpdated(LocalDateTime.now());
        teamRepository.save(team);
    }
}
