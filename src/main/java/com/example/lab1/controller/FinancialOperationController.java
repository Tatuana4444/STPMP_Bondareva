package com.example.lab1.controller;

import com.example.lab1.service.OperationHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@RestController
@RequestMapping("/financialOperation")
public class FinancialOperationController {

    private final OperationHistoryService operationHistoryService;



    @Autowired
    public FinancialOperationController(OperationHistoryService operationHistoryService) {
        this.operationHistoryService = operationHistoryService;
    }

    @GetMapping
    public ResponseEntity<String> getToday(){

        return ResponseEntity.ok(operationHistoryService.getToday());
    }

    @PutMapping("closeDay")
    public ResponseEntity<String> closeDay(){
        return ResponseEntity.ok(operationHistoryService.closeDay());
    }
}
