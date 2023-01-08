package com.example.lab1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractDTO {
    private Long id;

    private String StartDate;

    private String EndDate;

    private String ContractType;

    private String User;

    private String Currency;

    private double Sum;

    private double Percent;

    private String CurrentAccount;

    private String PersentAccount;
}
