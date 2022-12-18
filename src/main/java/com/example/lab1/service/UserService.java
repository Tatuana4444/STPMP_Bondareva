package com.example.lab1.service;

import com.example.lab1.dto.UserDTO;
import com.example.lab1.exceptions.ResourceNotFoundException;
import com.example.lab1.model.User;
import com.example.lab1.repositories.*;
import com.example.lab1.utils.UserMappingUtils;
import com.example.lab1.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {
    private final UserRepository userRepository;

    private final UserMappingUtils userMappingUtils;

    private final ValidationUtils validationUtils;

    @Autowired
    public UserService(UserRepository userRepository,
                       CityRepository cityRepository,
                       FamilyStatusRepository familyStatusRepository,
                       InvalidityRepository invalidityRepository,
                       NationalityRepository nationalityRepository) {
        this.userRepository = userRepository;
        this.userMappingUtils = new UserMappingUtils(cityRepository, familyStatusRepository, invalidityRepository, nationalityRepository);
        this.validationUtils = new ValidationUtils(userRepository);
    }


    public UserDTO createUser(UserDTO userDTO) {

        if(validationUtils.isUserValidCreate(userDTO)) {
            User user = userMappingUtils.mapToClient(userDTO);
            User savedUser = this.userRepository.save(user);
            return userMappingUtils.mapToUserDto(savedUser);
        }
        else
        {
            return null;
        }
    }

    public List<UserDTO> getUsers(){
        return this.userRepository.findAll().stream()
                .map(userMappingUtils::mapToUserDto).sorted((a, b) -> a.getLastname().compareToIgnoreCase(b.getLastname())).collect(Collectors.toList());
    }

    public UserDTO getUser(Long id){
        User user = this.userRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("User not found")
        );

        return  userMappingUtils.mapToUserDto(user);
    }

    public UserDTO updateUser(UserDTO newUser, Long id){
        return this.userRepository.findById(id)
                .map(user -> {
                    if(validationUtils.isUserValidUpdate(newUser)) {
                        user = userMappingUtils.mapToClient(newUser);
                        return userMappingUtils.mapToUserDto(this.userRepository.save(user));
                    }
                    else
                    {
                        return null;
                    }
                })
                .orElseGet(()->{
                    newUser.setId(id);
                    if(validationUtils.isUserValidCreate(newUser)) {
                        User user = userMappingUtils.mapToClient(newUser);
                        return userMappingUtils.mapToUserDto(this.userRepository.save(user));
                    }
                    else
                    {
                        return null;
                    }
                });
    }

    public void removeUser(Long id){
        this.userRepository.deleteById(id);
    }
}
