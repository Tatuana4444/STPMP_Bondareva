package com.example.lab1.repositories;

import com.example.lab1.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {

    Account findByAccountNumber(String AccountNumber);
}