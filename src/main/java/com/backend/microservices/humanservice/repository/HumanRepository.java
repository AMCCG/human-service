package com.backend.microservices.humanservice.repository;

import com.backend.microservices.humanservice.model.entity.HumanEntity;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
@AllArgsConstructor
public class HumanRepository {
    private final JdbcTemplate jdbcTemplate;

    public int create(HumanEntity humanEntity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String insertHumanSql = "INSERT INTO human (idCard, thaiTitle, thaiFirstName, thaiLastName, englishTitle, englishFirstName, englishLastName, dateOfBirth, address, email, phone) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertHumanSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, humanEntity.getIdCard());
            ps.setString(2, humanEntity.getThaiTitle());
            ps.setString(3, humanEntity.getThaiFirstName());
            ps.setString(4, humanEntity.getThaiLastName());
            ps.setString(5, humanEntity.getEnglishTitle());
            ps.setString(6, humanEntity.getEnglishFirstName());
            ps.setString(7, humanEntity.getEnglishLastName());
            if (null == humanEntity.getDateOfBirth()) {
                ps.setDate(8, null);
            } else {
                Date dob = new Date(humanEntity.getDateOfBirth().getTime());
                ps.setDate(8, dob);
            }
            ps.setString(9, humanEntity.getAddress());
            ps.setString(10, humanEntity.getEmail());
            ps.setString(11, humanEntity.getPhone());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    public List<HumanEntity> getAllHuman() {
        var rowMapper = BeanPropertyRowMapper.newInstance(HumanEntity.class);
        String sql = "SELECT h.* FROM human h order by id desc";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public HumanEntity getHumanById(int id) {
        var rowMapper = BeanPropertyRowMapper.newInstance(HumanEntity.class);
        String sql = "SELECT h.* FROM human h WHERE h.id = ? order by id desc";
        List<HumanEntity> humanEntities = jdbcTemplate.query(sql, rowMapper, id);
        if (humanEntities.isEmpty()) {
            return null;
        }
        return humanEntities.get(0);
    }
}
