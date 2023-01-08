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
public class OperationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private long fromAccount;

    private long toAccount;

    private double MoneyTransfer;

    private  String Comments;

    private Calendar date;

    private long CurrencyId;

    public OperationHistory(long fromAccount,
                            long toAccount,
                            double moneyTransfer,
                            String comments,
                            Calendar date,
                            long currencyId) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        MoneyTransfer = moneyTransfer;
        Comments = comments;
        this.date = date;
        CurrencyId = currencyId;
    }
}
