package com.example.lab1.repositories;

import com.example.lab1.model.Invalidity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidityRepository extends JpaRepository<Invalidity,Long> {
    Invalidity findByName(String name);
}
