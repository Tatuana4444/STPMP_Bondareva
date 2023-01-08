package com.example.lab1.controller;

import com.example.lab1.dto.ContractDTO;
import com.example.lab1.dto.OperationHistoryDTO;
import com.example.lab1.service.OperationHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/operationHistories")
public class OperationHistoryController {
    private final OperationHistoryService operationHistoryService;

    @Autowired
    public OperationHistoryController(OperationHistoryService operationHistoryService){
        this.operationHistoryService = operationHistoryService;
    }

    @PostMapping
    public ResponseEntity<OperationHistoryDTO> createOperationHistory(@RequestBody OperationHistoryDTO operationHistoryDTO) throws URISyntaxException {

        OperationHistoryDTO createdOperationHistory = operationHistoryService.createOperationHistory(operationHistoryDTO);
        if(createdOperationHistory != null) {

            return ResponseEntity.created(new URI("/operationHistories/" + createdOperationHistory.getId())).body(createdOperationHistory);
        }
        else
        {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<OperationHistoryDTO>> getOperationHistories(){
        return ResponseEntity.ok(operationHistoryService.getOperationHistories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperationHistoryDTO> getOperationHistory(@PathVariable(value = "id" ) Long id){
        return ResponseEntity.ok(operationHistoryService.getOperationHistory(id));
    }

    @GetMapping("contract/{id}")
    public ResponseEntity<List<OperationHistoryDTO>> getOperationHistoryNyContract(@PathVariable(value = "id" ) Long id){
        return ResponseEntity.ok(operationHistoryService.getOperationHistoryByContract(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OperationHistoryDTO> updateOperationHistory(@RequestBody OperationHistoryDTO newOperationHistory, @PathVariable(value = "id") Long id){
        OperationHistoryDTO updatedOperationHistory = operationHistoryService.updateOperationHistory(newOperationHistory, id);
        if(updatedOperationHistory != null) {
            return ResponseEntity.ok().body(updatedOperationHistory);
        }
        else
        {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeOperationHistory(@PathVariable(value = "id") Long id){
        operationHistoryService.removeOperationHistory(id);
        return ResponseEntity.ok().build();
    }
}
