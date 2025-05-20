package org.example.subscriptions.service.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserTestDAO {
    private NamedParameterJdbcTemplate jdbcTemplate;

    public UserTestDAO(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void truncateTableUsers() {
        jdbcTemplate.update(
                "TRUNCATE TABLE users CASCADE",
                new MapSqlParameterSource()
        );
    }
}
