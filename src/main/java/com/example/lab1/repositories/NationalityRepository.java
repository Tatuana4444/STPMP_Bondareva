package com.example.lab1.repositories;

import com.example.lab1.model.Invalidity;
import com.example.lab1.model.Nationality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NationalityRepository extends JpaRepository<Nationality,Long> {
    Nationality findByName(String name);
}
