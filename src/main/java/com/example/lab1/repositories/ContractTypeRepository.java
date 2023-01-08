package com.example.lab1.repositories;

import com.example.lab1.model.ContractType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractTypeRepository extends JpaRepository<ContractType,Long> {

    ContractType findByName(String name);
}