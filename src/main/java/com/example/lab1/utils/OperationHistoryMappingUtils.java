package com.example.lab1.utils;

import com.example.lab1.dto.OperationHistoryDTO;
import com.example.lab1.model.OperationHistory;
import com.example.lab1.repositories.AccountRepository;
import com.example.lab1.repositories.CurrencyRepository;
import com.example.lab1.repositories.OperationHistoryRepository;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OperationHistoryMappingUtils implements IMappingUtils<OperationHistory, OperationHistoryDTO>{

    private final AccountRepository accountRepository;
    private final CurrencyRepository currencyRepository;


    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public OperationHistoryMappingUtils(AccountRepository accountRepository, CurrencyRepository currencyRepository) {
        this.accountRepository = accountRepository;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public OperationHistoryDTO mapToDto(OperationHistory operationHistory) {
        OperationHistoryDTO dto = new OperationHistoryDTO();
        dto.setId(operationHistory.getId());
        if(operationHistory.getFromAccount() != 0) {
            dto.setFromAccountNum(accountRepository.findById(operationHistory.getFromAccount()).get().getAccountNumber());
        }
        if(operationHistory.getToAccount() != 0) {
            dto.setToAccountNum(accountRepository.findById(operationHistory.getToAccount()).get().getAccountNumber());
        }
        dto.setMoneyTransfer(Math.round(operationHistory.getMoneyTransfer()*100d)/100d);
        dto.setComments(operationHistory.getComments());
        dto.setDate(dateFormat.format(operationHistory.getDate().getTime()));
        dto.setCurrency(currencyRepository.findById(operationHistory.getCurrencyId()).get().getName());

        return dto;
    }

    @Override
    public OperationHistory mapToEntity(OperationHistoryDTO dto) {
        OperationHistory operationHistory = new OperationHistory();
        operationHistory.setId(dto.getId());
        if(!dto.getFromAccountNum().isEmpty()) {
            operationHistory.setFromAccount(accountRepository.findByAccountNumber(dto.getFromAccountNum()).getId());
        }
        else {
            operationHistory.setFromAccount(0);
        }
        if(!dto.getToAccountNum().isEmpty()) {
            operationHistory.setToAccount(accountRepository.findByAccountNumber(dto.getToAccountNum()).getId());
        }
        else {
            operationHistory.setToAccount(0);
        }
        operationHistory.setMoneyTransfer(Math.round(dto.getMoneyTransfer()*100d)/100d);
        operationHistory.setComments(dto.getComments());

        Calendar dateCalendar =  Calendar.getInstance();
        dateCalendar.setTime(Date.valueOf(dto.getDate()));
        operationHistory.setDate(dateCalendar);
        operationHistory.setCurrencyId(currencyRepository.findByName(dto.getCurrency()).getId());

        return operationHistory;
    }
}
