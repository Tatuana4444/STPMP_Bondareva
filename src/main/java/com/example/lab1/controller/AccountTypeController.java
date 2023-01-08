package com.example.lab1.controller;

import com.example.lab1.exceptions.ResourceNotFoundException;
import com.example.lab1.model.AccountType;
import com.example.lab1.repositories.AccountTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/accountTypes")
public class AccountTypeController {
    private AccountTypeRepository accountTypeRepository;

    @Autowired
    public AccountTypeController(AccountTypeRepository accountTypeRepository){
        this.accountTypeRepository = accountTypeRepository;
    }

    @PostMapping()
    public AccountType saveAccountType(@RequestBody AccountType accountType){
        return this.accountTypeRepository.save(accountType);
    }

    @GetMapping()
    public ResponseEntity<List<AccountType>> getCities(){
        return ResponseEntity.ok(
                this.accountTypeRepository.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountType> getAccountType(@PathVariable(value = "id" ) Long id){
        AccountType accountType = this.accountTypeRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Account type not found")
        );

        return  ResponseEntity.ok().body(accountType);
    }

    @PutMapping("/{id}")
    public AccountType updateAccountType(@RequestBody AccountType newAccountType, @PathVariable(value = "id") Long id){
        return this.accountTypeRepository.findById(id)
                .map(accountType -> {
                    accountType.setName(newAccountType.getName());
                    return this.accountTypeRepository.save(accountType);
                })
                .orElseGet(()->{
                    newAccountType.setId(id);
                    return this.accountTypeRepository.save(newAccountType);
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAccountType(@PathVariable(value = "id") Long id){
        AccountType accountType =this.accountTypeRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("AccountType not found"+id)
        );

        this.accountTypeRepository.delete(accountType);
        return ResponseEntity.ok().build();
    }
}


