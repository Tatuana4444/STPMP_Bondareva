package com.example.lab1.repositories;

import com.example.lab1.model.OperationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OperationHistoryRepository extends JpaRepository<OperationHistory,Long> {

    Optional<OperationHistory> findFirstByFromAccountAndToAccountOrderByDateDesc(Long fromAccount, Long toAccount);

    List<OperationHistory> findAllByFromAccount(Long fromAccount);

    List<OperationHistory> findAllByToAccount(Long toAccount);
}
