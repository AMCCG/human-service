package com.backend.microservices.humanservice.repository;

import com.backend.microservices.humanservice.model.entity.HumanEntity;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class HumanRepository {
    private final JdbcTemplate jdbcTemplate;

    public int create(HumanEntity humanEntity) {
        String insertHumanSql = "INSERT INTO human (idCard, thaiTitle, thaiFirstName, thaiLastName, englishTitle, englishFirstName, englishLastName, dateOfBirth, address, email, phone) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(insertHumanSql, humanEntity.getIdCard(), humanEntity.getThaiTitle(), humanEntity.getThaiFirstName(), humanEntity.getEnglishLastName(), humanEntity.getEnglishTitle(), humanEntity.getEnglishFirstName(), humanEntity.getEnglishLastName(), humanEntity.getDateOfBirth(), humanEntity.getAddress(), humanEntity.getEmail(), humanEntity.getPhone());
    }
}
