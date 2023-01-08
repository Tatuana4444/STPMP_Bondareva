package com.example.lab1.repositories;

import com.example.lab1.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency,Long> {

    Currency findByName(String name);
}
