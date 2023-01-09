package com.example.lab1.repositories;

import com.example.lab1.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository  extends JpaRepository<Card,Long> {

    Optional<Card> findByNumber(String number);

    Optional<Card> findByAccountNumber(Long accountId);
}
