package com.example.lab1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private Long id;
    private String AccountTypeId;
    private String AccountNumber;
    private String PlanAccountNumber;
    private double Debit;
    private double Credit;
    private double Saldo;
    private String user;
    private String contractId;
}
