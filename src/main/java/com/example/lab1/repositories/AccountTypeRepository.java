package com.example.lab1.repositories;

import com.example.lab1.model.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTypeRepository extends JpaRepository<AccountType,Long> {

    AccountType findByName(String name);
}
