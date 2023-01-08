package com.example.lab1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Calendar;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Calendar StartDate;

    private Calendar EndDate;

    private long ContractTypeId;

    private long UserId;

    private long CurrencyId;

    private double Sum;

    private double Percent;

    private long currentAccountId;

    private long persentAccountId;

}
