package com.example.lab1.controller;

import com.example.lab1.exceptions.ResourceNotFoundException;
import com.example.lab1.model.Nationality;
import com.example.lab1.repositories.NationalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NationalityController {

    private NationalityRepository nationalityRepository;

    @Autowired
    public NationalityController(NationalityRepository nationalityRepository){
        this.nationalityRepository = nationalityRepository;
    }

    @PostMapping("/nationality/save")
    public Nationality saveNationality(@RequestBody Nationality nationality){
        return this.nationalityRepository.save(nationality);
    }

    @GetMapping("/nationality/all")
    public ResponseEntity<List<Nationality>> getCities(){
        return ResponseEntity.ok(
                this.nationalityRepository.findAll()
        );
    }

    @GetMapping("/nationality/{id}")
    public ResponseEntity<Nationality> getNationality(@PathVariable(value = "id" ) Long id){
        Nationality nationality = this.nationalityRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Nationality not found")
        );

        return  ResponseEntity.ok().body(nationality);
    }

    @PutMapping("nationality/{id}")
    public Nationality updateNationality(@RequestBody Nationality newNationality, @PathVariable(value = "id") Long id){
        return this.nationalityRepository.findById(id)
                .map(nationality -> {
                    nationality.setName(newNationality.getName());
                    return this.nationalityRepository.save(nationality);
                })
                .orElseGet(()->{
                    newNationality.setId(id);
                    return this.nationalityRepository.save(newNationality);
                });
    }

    @DeleteMapping("nationality/{id}")
    public ResponseEntity<Void> removeNationality(@PathVariable(value = "id") Long id){
        Nationality nationality =this.nationalityRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Nationality not found"+id)
        );

        this.nationalityRepository.delete(nationality);
        return ResponseEntity.ok().build();
    }
}
