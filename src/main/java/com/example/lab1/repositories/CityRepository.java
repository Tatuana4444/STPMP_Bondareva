package com.example.lab1.repositories;

import com.example.lab1.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City,Long> {

    City findByName(String name);
}

