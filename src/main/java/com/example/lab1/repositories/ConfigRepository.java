package com.example.lab1.repositories;

import com.example.lab1.model.Config;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigRepository extends JpaRepository<Config,Long> {
}
