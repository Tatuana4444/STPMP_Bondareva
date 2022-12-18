package com.example.lab1.repositories;

import com.example.lab1.model.City;
import com.example.lab1.model.FamilyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyStatusRepository extends JpaRepository<FamilyStatus,Long> {
    FamilyStatus findByName(String name);
}
