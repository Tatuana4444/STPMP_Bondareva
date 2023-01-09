package com.example.lab1.controller;

import com.example.lab1.exceptions.ResourceNotFoundException;
import com.example.lab1.model.Config;
import com.example.lab1.model.Currency;
import com.example.lab1.repositories.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/config")
public class ConfigController {

    private final ConfigRepository configRepository;

    @Autowired
    public ConfigController(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    @PutMapping("setWithdrawalThroughCash/{value}")
    public ResponseEntity<Boolean> setWithdrawalThroughCash(@PathVariable(value = "value" ) boolean value){
        Config config = this.configRepository.findAll().get(0);
        config.setWithdrawalThroughCash(value);
        config = this.configRepository.save(config);
        return  ResponseEntity.ok().body(config.isWithdrawalThroughCash());
    }

    @GetMapping("isWithdrawalThroughCash/")
    public ResponseEntity<Boolean> setWithdrawalThroughCash(){
        Config config = this.configRepository.findAll().get(0);
        return  ResponseEntity.ok().body(config.isWithdrawalThroughCash());
    }

}
