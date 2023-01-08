package com.example.lab1.utils;

import com.example.lab1.dto.AccountDTO;
import com.example.lab1.model.Account;
import com.example.lab1.model.Contract;
import com.example.lab1.model.User;

public interface IMappingUtils<T, Tdto> {
    public Tdto mapToDto(T entity);

    public T mapToEntity(Tdto dto);
}
