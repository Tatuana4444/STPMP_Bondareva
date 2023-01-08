package com.example.lab1.repositories;

import com.example.lab1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByPassportNumber(String passportNumber);
    User findByIdentifiedNumber(String identifiedNumber);
    User findByLastnameAndFirstnameAndSurname(String lastname, String firstname, String surname);
}
