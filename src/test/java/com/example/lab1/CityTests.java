package com.example.lab1;

import com.example.lab1.model.City;
import com.example.lab1.model.User;
import com.example.lab1.repositories.CityRepository;
import com.example.lab1.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Calendar;
import java.util.GregorianCalendar;

@DataJpaTest
public class CityTests {

    @Autowired
    private CityRepository cityRepository;

    @Test
    public void testSaveCity(){
        City city = new City("Minsk");
        city = cityRepository.save(city);

        cityRepository.findById(city.getId())
                .map(newCity ->{
                    Assertions.assertEquals("Minsk", newCity.getName());
                    return true;
                });
    }
}
