package com.example.lab1.controller;


import com.example.lab1.exceptions.ResourceNotFoundException;
import com.example.lab1.model.City;
import com.example.lab1.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CityController {
    private CityRepository cityRepository;

    @Autowired
    public CityController(CityRepository cityRepository){
        this.cityRepository = cityRepository;
    }

    @PostMapping("/city/save")
    public City saveCity(@RequestBody City city){
        return this.cityRepository.save(city);
    }

    @GetMapping("/city/all")
    public ResponseEntity<List<City>> getCities(){
        return ResponseEntity.ok(
                this.cityRepository.findAll()
        );
    }

    @GetMapping("/city/{id}")
    public ResponseEntity<City> getCity(@PathVariable(value = "id" ) Long id){
        City city = this.cityRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("City not found")
        );

        return  ResponseEntity.ok().body(city);
    }

    @PutMapping("city/{id}")
    public City updateCity(@RequestBody City newCity, @PathVariable(value = "id") Long id){
        return this.cityRepository.findById(id)
                .map(city -> {
                    city.setName(newCity.getName());
                    return this.cityRepository.save(city);
                })
                .orElseGet(()->{
                    newCity.setId(id);
                    return this.cityRepository.save(newCity);
                });
    }

    @DeleteMapping("city/{id}")
    public ResponseEntity<Void> removeCity(@PathVariable(value = "id") Long id){
        City city =this.cityRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("City not found"+id)
        );

        this.cityRepository.delete(city);
        return ResponseEntity.ok().build();
    }
}
