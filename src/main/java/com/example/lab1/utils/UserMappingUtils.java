package com.example.lab1.utils;

import com.example.lab1.dto.AccountDTO;
import com.example.lab1.model.Account;
import com.example.lab1.model.User;
import com.example.lab1.dto.UserDTO;
import com.example.lab1.repositories.CityRepository;
import com.example.lab1.repositories.FamilyStatusRepository;
import com.example.lab1.repositories.InvalidityRepository;
import com.example.lab1.repositories.NationalityRepository;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UserMappingUtils implements IMappingUtils<User, UserDTO>{

    private final CityRepository cityRepository;
    private final FamilyStatusRepository familyStatusRepository;
    private final InvalidityRepository invalidityRepository;
    private final NationalityRepository nationalityRepository;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public UserMappingUtils(CityRepository cityRepository, FamilyStatusRepository familyStatusRepository, InvalidityRepository invalidityRepository, NationalityRepository nationalityRepository) {
        this.cityRepository = cityRepository;
        this.familyStatusRepository = familyStatusRepository;
        this.invalidityRepository = invalidityRepository;
        this.nationalityRepository = nationalityRepository;
    }

    @Override
    public UserDTO mapToDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setLastname(user.getLastname());
        dto.setFirstname(user.getFirstname());
        dto.setSurname(user.getSurname());
        dto.setBirthday(dateFormat.format(user.getBirthday().getTime()));
        dto.setSeries(user.getSeries());
        dto.setPassportNumber(user.getPassportNumber());
        dto.setIssued(user.getIssued());
        dto.setIssueDate(dateFormat.format(user.getIssueDate().getTime()));
        dto.setIdentifiedNumber(user.getIdentifiedNumber());
        dto.setBirthPlace(user.getBirthPlace());
        dto.setCity(cityRepository.findById(user.getCityId()).get().getName());
        dto.setAddress(user.getAddress());
        dto.setHomePhone(user.getHomePhone());
        dto.setMobilePhone(user.getMobilePhone());
        dto.setEmail(user.getEmail());
        dto.setWorkPlace(user.getWorkPlace());
        dto.setPosition(user.getPosition());

        dto.setFamilyStatus(familyStatusRepository.findById(user.getFamilyStatusId()).get().getName());
        dto.setRegistrationCity(cityRepository.findById(user.getRegistrationCityId()).get().getName());
        dto.setNationality(nationalityRepository.findById(user.getNationalityId()).get().getName());
        dto.setInvalidity(invalidityRepository.findById(user.getInvaliditiyId()).get().getName());
        dto.setPensioner(user.isPensioner());
        dto.setIncome(String.valueOf(user.getIncome()));
        return dto;
    }

    @Override
    public User mapToEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setLastname(dto.getLastname());
        user.setFirstname(dto.getFirstname());
        user.setSurname(dto.getSurname());
        Calendar birthdayCalendar =  Calendar.getInstance();
        birthdayCalendar.setTime(Date.valueOf(dto.getBirthday()));
        user.setBirthday(birthdayCalendar);
        user.setSeries(dto.getSeries());
        user.setPassportNumber(dto.getPassportNumber());
        user.setIssued(dto.getIssued());
        Calendar issueCalendar =  Calendar.getInstance();
        issueCalendar.setTime(Date.valueOf(dto.getIssueDate()));
        user.setIssueDate(issueCalendar);
        user.setIdentifiedNumber(dto.getIdentifiedNumber());
        user.setBirthPlace(dto.getBirthPlace());;
        user.setCityId(cityRepository.findByName(dto.getCity()).getId());

        user.setAddress(dto.getAddress());
        user.setHomePhone(dto.getHomePhone());
        user.setMobilePhone(dto.getMobilePhone());
        user.setEmail(dto.getEmail());
        user.setWorkPlace(dto.getWorkPlace());
        user.setPosition(dto.getPosition());

        user.setFamilyStatusId(familyStatusRepository.findByName(dto.getFamilyStatus()).getId());
        user.setRegistrationCityId(cityRepository.findByName(dto.getRegistrationCity()).getId());
        user.setNationalityId(nationalityRepository.findByName(dto.getNationality()).getId());
        user.setInvaliditiyId(invalidityRepository.findByName(dto.getInvalidity()).getId());

        user.setPensioner(dto.isPensioner());
        if(!dto.getIncome().isEmpty()) {
            user.setIncome(Double.parseDouble(dto.getIncome()));
        }

        return user;
    }
}
