package com.ivchern.exchange_employers.Controllers;

import com.ivchern.exchange_employers.DTO.UserDTO;
import com.ivchern.exchange_employers.Model.User.User;
import com.ivchern.exchange_employers.Services.User.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@SecurityRequirement(name = "JWT")
@Tag(name = "User", description = "Данные пользователя")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/{id}", consumes = "application/json")
    public UserDTO updateUser(@PathVariable("id") Long id, @RequestBody UserDTO userDTO){
        return userService.update(userDTO, id);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        Optional<UserDTO> optUser = userService.findById(id);
        if (optUser.isPresent()){
            return new ResponseEntity<UserDTO>(optUser.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/GetByUsername/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username){
        Optional<UserDTO> optUser = userService.findByUsername(username);
        if (optUser.isPresent()){
            return new ResponseEntity<UserDTO>(optUser.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}
