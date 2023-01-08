package com.example.lab1.controller;

import com.example.lab1.dto.AccountDTO;
import com.example.lab1.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) throws URISyntaxException {

        AccountDTO createdAccount = accountService.createAccount(accountDTO);
        if(createdAccount != null) {

            return ResponseEntity.created(new URI("/accounts/" + createdAccount.getId())).body(createdAccount);
        }
        else
        {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAccounts(){
        return ResponseEntity.ok(accountService.getAccounts());
    }

    @GetMapping("/{accountNum}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable(value = "accountNum" ) String accountNum){
        return ResponseEntity.ok(accountService.getAccount(accountNum));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> updateAccount(@RequestBody AccountDTO newAccount, @PathVariable(value = "id") Long id){
        AccountDTO updatedAccount = accountService.updateAccount(newAccount, id);
        if(updatedAccount != null) {
            return ResponseEntity.ok().body(updatedAccount);
        }
        else
        {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAccount(@PathVariable(value = "id") Long id){
        accountService.removeAccount(id);
        return ResponseEntity.ok().build();
    }
}