package com.example.lab1.service;

import com.example.lab1.dto.AccountDTO;
import com.example.lab1.exceptions.ResourceNotFoundException;
import com.example.lab1.model.Account;
import com.example.lab1.model.Card;
import com.example.lab1.repositories.*;
import com.example.lab1.utils.AccountMappingUtils;
import com.example.lab1.utils.IMappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AccountService {
    private final AccountRepository accountRepository;

    private final IMappingUtils<Account, AccountDTO> accountMappingUtils;

    private final CardRepository cardRepository;

    private final CardService cardService;

    @Autowired
    public AccountService(AccountRepository accountRepository,
                          ContractRepository contractRepository,
                          AccountTypeRepository accountTypeRepository,
                          UserRepository userRepository,
                          CardRepository cardRepository,
                          CardService cardService) {
        this.accountRepository = accountRepository;
        this.cardRepository = cardRepository;
        this.cardService = cardService;
        this.accountMappingUtils = new AccountMappingUtils(accountTypeRepository, contractRepository, userRepository);
    }


    public AccountDTO createAccount(AccountDTO accountDTO) {

        Account account = accountMappingUtils.mapToEntity(accountDTO);
        Account savedAccount = this.accountRepository.save(account);

        Card card = new Card(0L, cardService.generateCardNumber(), cardService.generatePIN(), savedAccount.getAccountNumber());
        cardRepository.save(card);
        return accountMappingUtils.mapToDto(savedAccount);
    }

    public List<AccountDTO> getAccounts(){
        return this.accountRepository.findAll().stream()
                .map(accountMappingUtils::mapToDto)
                .collect(Collectors.toList());
    }

    public AccountDTO getAccount(Long id){
        Account account = this.accountRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Account not found")
        );

        return  accountMappingUtils.mapToDto(account);
    }

    public AccountDTO getAccount(String accountNum){
        Account account = this.accountRepository.findByAccountNumber(accountNum);
        return  accountMappingUtils.mapToDto(account);
    }

    public AccountDTO updateAccount(AccountDTO newAccount, Long id){
        return this.accountRepository.findById(id)
                .map(account -> {
                    account = accountMappingUtils.mapToEntity(newAccount);
                    return accountMappingUtils.mapToDto(this.accountRepository.save(account));
                })
                .orElseGet(()->{
                    newAccount.setId(id);
                    Account account = accountMappingUtils.mapToEntity(newAccount);
                    return accountMappingUtils.mapToDto(this.accountRepository.save(account));
                });
    }

    public void removeAccount(Long id){
        this.accountRepository.deleteById(id);
    }
}

