package com.example.lab1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String lastname;
    private String firstname;
    private String surname;
    private String birthday;
    private String series;
    private String passportNumber;
    private String issued;
    private String issueDate;
    private String identifiedNumber;
    private String birthPlace;
    private String city;
    private String address;
    private String homePhone;
    private String mobilePhone;
    private String email;
    private String workPlace;
    private String position;
    private String registrationCity;
    private String familyStatus;
    private String nationality;
    private String invalidity;
    private boolean pensioner;
    private String income;
}
