package com.example.lab1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationHistoryDTO {
    private Long id;

    private String FromAccountNum;

    private String ToAccountNum;

    private double MoneyTransfer;

    private  String Comments;

    private String Date;

    private String Currency;
}
