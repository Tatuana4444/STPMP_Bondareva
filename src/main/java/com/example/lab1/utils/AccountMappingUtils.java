package com.example.lab1.utils;

import com.example.lab1.dto.AccountDTO;
import com.example.lab1.model.Account;
import com.example.lab1.model.Contract;
import com.example.lab1.model.User;
import com.example.lab1.repositories.AccountTypeRepository;
import com.example.lab1.repositories.ContractRepository;
import com.example.lab1.repositories.UserRepository;

import java.util.Optional;

public class AccountMappingUtils implements IMappingUtils<Account, AccountDTO>{

    private final AccountTypeRepository accountTypeRepository;
    private final ContractRepository contractRepository;
    private final UserRepository userRepository;

    public AccountMappingUtils(AccountTypeRepository accountTypeRepository, ContractRepository contractRepository, UserRepository userRepository) {
        this.accountTypeRepository = accountTypeRepository;
        this.contractRepository = contractRepository;
        this.userRepository = userRepository;
    }


    @Override
    public AccountDTO mapToDto(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setPlanAccountNumber(account.getPlanAccountNumber());
        dto.setAccountTypeId(accountTypeRepository.findById(account.getAccountTypeId()).get().getName());
        dto.setCredit(Math.round(account.getCredit()*100d)/100d);
        dto.setDebit(Math.round(account.getDebit()*100d)/100d);
        dto.setSaldo(Math.round(account.getSaldo()*100d)/100d);
        Optional<Contract> contract = contractRepository.findByCurrentAccountId(account.getId());
        if(!contract.isPresent()) {
            contract = contractRepository.findByPersentAccountId(account.getId());
        }

        if(contract.isPresent()){
            User user = userRepository.findById(contract.get().getUserId()).get();
            dto.setUser(user.getLastname() + ' ' + user.getFirstname() + ' ' + user.getSurname());
            dto.setContractId(contract.get().getId().toString());
        }
        return dto;
    }

    @Override
    public Account mapToEntity(AccountDTO dto) {
        Account account = new Account();
        account.setId(dto.getId());
        account.setId(dto.getId());
        account.setAccountNumber(dto.getAccountNumber());
        account.setPlanAccountNumber(dto.getPlanAccountNumber());
        account.setAccountTypeId(accountTypeRepository.findByName(dto.getAccountTypeId()).getId());
        account.setCredit(dto.getCredit());
        account.setDebit(dto.getDebit());
        account.setSaldo(dto.getSaldo());
        return account;
    }
}
