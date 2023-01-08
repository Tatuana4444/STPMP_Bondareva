package com.example.lab1.utils;

import com.example.lab1.dto.ContractDTO;
import com.example.lab1.model.Contract;
import com.example.lab1.model.User;
import com.example.lab1.repositories.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ContractMappingUtils implements IMappingUtils<Contract, ContractDTO> {

    private final ContractTypeRepository contractTypeRepository;
    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;
    private final AccountRepository accountRepository;

    private long currentAccountId;
    private long percentAccountId;

    public void setCurrentAccountId(long currentAccountId){
        this.currentAccountId = currentAccountId;
    }

    public void setPercentAccountId(long percentAccountId){
        this.percentAccountId = percentAccountId;
    }
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public ContractMappingUtils(ContractTypeRepository contractTypeRepository, UserRepository userRepository, CurrencyRepository currencyRepository, AccountRepository accountRepository) {
        this.contractTypeRepository = contractTypeRepository;
        this.userRepository = userRepository;
        this.currencyRepository = currencyRepository;
        this.accountRepository = accountRepository;
    }


    @Override
    public ContractDTO mapToDto(Contract contract) {
        ContractDTO dto = new ContractDTO();
        dto.setId(contract.getId());
        dto.setStartDate(dateFormat.format(contract.getStartDate().getTime()));
        dto.setEndDate(dateFormat.format(contract.getEndDate().getTime()));

        dto.setContractType(contractTypeRepository.findById(contract.getContractTypeId()).get().getName());
        User user = userRepository.findById(contract.getUserId()).get();
        dto.setUser(user.getLastname() + ' ' + user.getFirstname() + ' ' + user.getSurname());
        dto.setCurrency(currencyRepository.findById(contract.getCurrencyId()).get().getName());
        dto.setSum(contract.getSum());
        dto.setPercent(contract.getPercent());
        dto.setCurrentAccount(accountRepository.findById(contract.getCurrentAccountId()).get().getAccountNumber());
        dto.setPersentAccount(accountRepository.findById(contract.getPersentAccountId()).get().getAccountNumber());

        return dto;
    }

    @Override
    public Contract mapToEntity(ContractDTO dto) {
        Contract contract = new Contract();
        contract.setId(dto.getId());

        Calendar startDateCalendar =  Calendar.getInstance();
        startDateCalendar.setTime(Date.valueOf(dto.getStartDate()));
        contract.setStartDate(startDateCalendar);

        Calendar endDateCalendar =  Calendar.getInstance();
        endDateCalendar.setTime(Date.valueOf(dto.getEndDate()));
        contract.setEndDate(endDateCalendar);

        contract.setContractTypeId(contractTypeRepository.findByName(dto.getContractType()).getId());

        String[] name = dto.getUser().split(" ");
        contract.setUserId(userRepository.findByLastnameAndFirstnameAndSurname(name[0], name[1], name[2]).getId());
        contract.setCurrencyId(currencyRepository.findByName(dto.getCurrency()).getId());
        contract.setSum(dto.getSum());
        contract.setPercent(dto.getPercent());
        if(this.currentAccountId != 0) {
            contract.setCurrentAccountId(this.currentAccountId);
        }
        if(this.percentAccountId != 0) {
            contract.setPersentAccountId(this.percentAccountId);
        }

        return contract;
    }
}
