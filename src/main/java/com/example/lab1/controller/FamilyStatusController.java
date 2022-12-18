package com.example.lab1.controller;

import com.example.lab1.exceptions.ResourceNotFoundException;
import com.example.lab1.model.FamilyStatus;
import com.example.lab1.repositories.FamilyStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FamilyStatusController {
    private FamilyStatusRepository familyStatusRepository;

    @Autowired
    public FamilyStatusController(FamilyStatusRepository familyStatusRepository){
        this.familyStatusRepository = familyStatusRepository;

    }

    @PostMapping("/familyStatus/save")
    public FamilyStatus saveFamilyStatus(@RequestBody FamilyStatus familyStatus){
        return this.familyStatusRepository.save(familyStatus);
    }

    @GetMapping("/familyStatus/all")
    public ResponseEntity<List<FamilyStatus>> getCities(){
        return ResponseEntity.ok(
                this.familyStatusRepository.findAll()
        );
    }

    @GetMapping("/familyStatus/{id}")
    public ResponseEntity<FamilyStatus> getFamilyStatus(@PathVariable(value = "id" ) Long id){
        FamilyStatus familyStatus = this.familyStatusRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("FamilyStatus not found")
        );

        return  ResponseEntity.ok().body(familyStatus);
    }

    @PutMapping("familyStatus/{id}")
    public FamilyStatus updateFamilyStatus(@RequestBody FamilyStatus newFamilyStatus, @PathVariable(value = "id") Long id){
        return this.familyStatusRepository.findById(id)
                .map(familyStatus -> {
                    familyStatus.setName(newFamilyStatus.getName());
                    return this.familyStatusRepository.save(familyStatus);
                })
                .orElseGet(()->{
                    newFamilyStatus.setId(id);
                    return this.familyStatusRepository.save(newFamilyStatus);
                });
    }

    @DeleteMapping("familyStatus/{id}")
    public ResponseEntity<Void> removeFamilyStatus(@PathVariable(value = "id") Long id){
        FamilyStatus familyStatus =this.familyStatusRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("FamilyStatus not found"+id)
        );

        this.familyStatusRepository.delete(familyStatus);
        return ResponseEntity.ok().build();
    }
}
