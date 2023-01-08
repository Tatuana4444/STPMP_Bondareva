package com.example.lab1.repositories;

import com.example.lab1.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract,Long> {
    Contract findByCurrentAccountId(long currentAccountId);

    Contract findByPersentAccountId(long persentAccountId);
}
