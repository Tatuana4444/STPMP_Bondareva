package com.example.lab1.utils;

import com.example.lab1.dto.UserDTO;
import com.example.lab1.model.User;
import com.example.lab1.repositories.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ValidationUtils {
    private final UserRepository userRepository;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public ValidationUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public  boolean isText(String s){
        return  s != null && !s.isEmpty() && s.matches("^[a-zA-Zа-яА-Я]*$");
    }

    public boolean isMobilePhoneValid(String s)
    {
        return  s != null && !s.isEmpty() && s.matches("^\\+375\\([0-9]{2}\\)-[0-9]{3}-[0-9]{2}-[0-9]{2}$");
    }

    public boolean isHomePhoneValid(String s)
    {
        return  s != null && !s.isEmpty() && s.matches("^[0-9]{2}-[0-9]{2}-[0-9]{2}$");
    }

    public boolean isIdentifiedNumber(String s)
    {
        return  s!= null && !s.isEmpty() && s.matches("^[0-9]{7}[A-Z][0-9]{3}[A-Z]{2}[0-9]$");
    }

    public boolean isPassportNumber(String s)
    {
        return  s!= null && !s.isEmpty() && s.matches("^[0-9]{7}$");
    }

    public  boolean isUserValidUpdate(UserDTO user){
        boolean ret;
        try {
            ret = isText(user.getLastname())
                    && isText(user.getFirstname())
                    && isText(user.getSurname());
            ret = ret && !user.getBirthday().isEmpty();
            dateFormat.parse(user.getBirthday());
            ret = ret && !user.getSeries().isEmpty();
            ret = ret && !user.getPassportNumber().isEmpty();
            ret = ret && !user.getIssued().isEmpty();
            ret = ret && !user.getIssueDate().isEmpty();
            dateFormat.parse(user.getIssueDate());
            ret = ret && !user.getIdentifiedNumber().isEmpty();
            ret = ret && !user.getBirthPlace().isEmpty();

            ret = ret && !user.getCity().isEmpty();
            ret = ret && !user.getAddress().isEmpty();
            ret = ret && !user.getRegistrationCity().isEmpty();
            ret = ret && !user.getFamilyStatus().isEmpty();
            ret = ret && !user.getNationality().isEmpty();
            ret = ret && !user.getInvalidity().isEmpty();
            ret = ret && (user.getMobilePhone().isEmpty() || isMobilePhoneValid(user.getMobilePhone()));
            ret = ret && (user.getHomePhone().isEmpty() || isHomePhoneValid(user.getHomePhone()));
            ret = ret && isIdentifiedNumber(user.getIdentifiedNumber());
            ret = ret && isPassportNumber(user.getPassportNumber());

        }
        catch (ParseException e) {
            ret = false;
        }

        return  ret;
    }

    public  boolean isUserValidCreate(UserDTO user){
        boolean ret;

        ret = this.isUserValidUpdate(user);
        ret = ret && userRepository.findByLastnameAndFirstnameAndSurname(user.getLastname(), user.getFirstname(), user.getSurname())==null;
        ret = ret && userRepository.findByPassportNumber(user.getPassportNumber()) == null;
        ret = ret && userRepository.findByIdentifiedNumber(user.getIdentifiedNumber()) == null;
        return  ret;
    }


}
