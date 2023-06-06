package com.ivchern.exchange_employers.Services.User;

import com.ivchern.exchange_employers.DTO.UserDTO;

import java.util.Optional;


public interface UserService {
    UserDTO update(UserDTO userDTO, Long id);
    Optional<UserDTO> findById(Long id);

    Optional<UserDTO> findByUsername(String username);



}
