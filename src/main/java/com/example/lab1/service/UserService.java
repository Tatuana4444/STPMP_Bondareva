package com.example.lab1.service;

import com.example.lab1.dto.UserDTO;
import com.example.lab1.exceptions.ResourceNotFoundException;
import com.example.lab1.model.User;
import com.example.lab1.repositories.*;
import com.example.lab1.utils.MappingUtils;
import com.example.lab1.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {
    private final UserRepository userRepository;

    private final MappingUtils mappingUtils;

    private final ValidationUtils validationUtils;

    @Autowired
    public UserService(UserRepository userRepository,
                       CityRepository cityRepository,
                       FamilyStatusRepository familyStatusRepository,
                       InvalidityRepository invalidityRepository,
                       NationalityRepository nationalityRepository) {
        this.userRepository = userRepository;
        this.mappingUtils = new MappingUtils(cityRepository, familyStatusRepository, invalidityRepository, nationalityRepository);
        this.validationUtils = new ValidationUtils(userRepository);
    }


    public UserDTO createUser(UserDTO userDTO) {

        if(validationUtils.isUserValidCreate(userDTO)) {
            User user = mappingUtils.mapToClient(userDTO);
            User savedUser = this.userRepository.save(user);
            return mappingUtils.mapToUserDto(savedUser);
        }
        else
        {
            return null;
        }
    }

    public List<UserDTO> getUsers(){
        return this.userRepository.findAll().stream()
                .map(mappingUtils::mapToUserDto).sorted((a, b) -> a.getLastname().compareToIgnoreCase(b.getLastname())).collect(Collectors.toList());
    }

    public UserDTO getUser(Long id){
        User user = this.userRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("User not found")
        );

        return  mappingUtils.mapToUserDto(user);
    }

    public UserDTO updateUser(UserDTO newUser, Long id){
        return this.userRepository.findById(id)
                .map(user -> {
                    if(validationUtils.isUserValidUpdate(newUser)) {
                        System.out.println("Yes");
                        user = mappingUtils.mapToClient(newUser);
                        return mappingUtils.mapToUserDto(this.userRepository.save(user));
                    }
                    else
                    {
                        System.out.println("No");
                        return null;
                    }
                })
                .orElseGet(()->{
                    newUser.setId(id);
                    if(validationUtils.isUserValidCreate(newUser)) {
                        System.out.println("Yes2");
                        User user = mappingUtils.mapToClient(newUser);
                        return mappingUtils.mapToUserDto(this.userRepository.save(user));
                    }
                    else
                    {
                        System.out.println("No2");
                        return null;
                    }
                });
    }

    public void removeUser(Long id){
        this.userRepository.deleteById(id);
    }
}
