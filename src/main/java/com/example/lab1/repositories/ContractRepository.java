package com.example.lab1.repositories;

import com.example.lab1.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract,Long> {
    Optional<Contract> findByCurrentAccountId(long currentAccountId);

    Optional<Contract> findByPersentAccountId(long persentAccountId);
}
