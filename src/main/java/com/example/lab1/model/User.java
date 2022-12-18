package com.example.lab1.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.Calendar;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String lastname;
    private String firstname;
    private String surname;
    private Calendar birthday;
    private String series;
    private String passportNumber;
    private String issued;
    private Calendar issueDate;
    private String identifiedNumber;
    private String birthPlace;
    private String address;
    @Nullable
    private String homePhone;
    @Nullable
    private String mobilePhone;
    @Nullable
    private String email;
    @Nullable
    private String workPlace;
    @Nullable
    private String position;
    private boolean pensioner;
    @Nullable
    private double income;

    private long cityId;

    private long registrationCityId;

    private long familyStatusId;

    private long nationalityId;

    private long invaliditiyId;


    public User(String lastname, String firstname, String surname, Calendar birthday, String series, String passportNumber, String issued,
                Calendar issueDate, String identifiedNumber, String birthPlace, String address, String homePhone,
                String mobilePhone, String email, String workPlace, String position, boolean pensioner, double income,
                long cityId, long registrationCityId, long familyStatusId, long nationalityId, long invaliditiyId){
        this.lastname = lastname;
        this.firstname = firstname;
        this.surname = surname;
        this.series = series;
        this.passportNumber = passportNumber;
        this.birthday = birthday;
        this.issued = issued;

        this.issueDate = issueDate;
        this.identifiedNumber = identifiedNumber;
        this.birthPlace = birthPlace;
        this.address = address;
        this.homePhone = homePhone;

        this.email = email;
        this.mobilePhone = mobilePhone;
        this.workPlace = workPlace;
        this.position = position;
        this.pensioner = pensioner;
        this.income = income;
        this.cityId = cityId;
        this.registrationCityId = registrationCityId;
        this.familyStatusId = familyStatusId;
        this.nationalityId = nationalityId;
        this.invaliditiyId = invaliditiyId;
    }
}
