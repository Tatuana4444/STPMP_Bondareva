package com.example.lab1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private long AccountTypeId;

    private String accountNumber;

    private String PlanAccountNumber;

    private double Debit;

    private double Credit;

    private double Saldo;

    public Account(long accountTypeId, String accountNumber, String planAccountNumber, double debit, double credit, double saldo) {
        AccountTypeId = accountTypeId;
        this.accountNumber = accountNumber;
        PlanAccountNumber = planAccountNumber;
        Debit = debit;
        Credit = credit;
        Saldo = saldo;
    }
}
