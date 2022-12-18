package com.example.lab1.controller;

import com.example.lab1.dto.UserDTO;
import com.example.lab1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) throws URISyntaxException {

        UserDTO createdUser = userService.createUser(userDTO);
        if(createdUser != null) {

            return ResponseEntity.created(new URI("/clients/" + createdUser.getId())).body(createdUser);
        }
        else
        {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable(value = "id" ) Long id){
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO newUser, @PathVariable(value = "id") Long id){
        UserDTO updatedUser = userService.updateUser(newUser, id);
        if(updatedUser != null) {
            return ResponseEntity.ok().body(updatedUser);
        }
        else
        {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable(value = "id") Long id){
        userService.removeUser(id);
        return ResponseEntity.ok().build();
    }
}