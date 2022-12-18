package com.example.lab1.controller;

import com.example.lab1.exceptions.ResourceNotFoundException;
import com.example.lab1.model.Invalidity;
import com.example.lab1.repositories.InvalidityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InvalidityController {
    private InvalidityRepository invalidityRepository;

    @Autowired
    public InvalidityController(InvalidityRepository invalidityRepository){
        this.invalidityRepository = invalidityRepository;
    }

    @PostMapping("/invalidity/save")
    public Invalidity saveInvalidity(@RequestBody Invalidity invalidity){
        return this.invalidityRepository.save(invalidity);
    }

    @GetMapping("/invalidity/all")
    public ResponseEntity<List<Invalidity>> getCities(){
        return ResponseEntity.ok(
                this.invalidityRepository.findAll()
        );
    }

    @GetMapping("/invalidity/{id}")
    public ResponseEntity<Invalidity> getInvalidity(@PathVariable(value = "id" ) Long id){
        Invalidity invalidity = this.invalidityRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Invalidity not found")
        );

        return  ResponseEntity.ok().body(invalidity);
    }

    @PutMapping("invalidity/{id}")
    public Invalidity updateInvalidity(@RequestBody Invalidity newInvalidity, @PathVariable(value = "id") Long id){
        return this.invalidityRepository.findById(id)
                .map(invalidity -> {
                    invalidity.setName(newInvalidity.getName());
                    return this.invalidityRepository.save(invalidity);
                })
                .orElseGet(()->{
                    newInvalidity.setId(id);
                    return this.invalidityRepository.save(newInvalidity);
                });
    }

    @DeleteMapping("invalidity/{id}")
    public ResponseEntity<Void> removeInvalidity(@PathVariable(value = "id") Long id){
        Invalidity invalidity =this.invalidityRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Invalidity not found"+id)
        );

        this.invalidityRepository.delete(invalidity);
        return ResponseEntity.ok().build();
    }
}
