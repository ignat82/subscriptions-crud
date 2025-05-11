package org.example.subscriptions.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.example.subscriptions.model.user.CreateUser;
import org.example.subscriptions.model.user.UpdateUser;
import org.example.subscriptions.model.user.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import static org.example.subscriptions.Utility.toInstant;

@Repository
public class UserDAO {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final String userFields = """
            id,
            username,
            first_name,
            middle_name,
            last_name,
            created_at,
            updated_at,
            deleted_at
            """;
    private final RowMapper<User> userRowMapper = (rs, rowNum) -> new User(
            UUID.fromString(rs.getString("id")),
            rs.getString("username"),
            rs.getString("first_name"),
            rs.getString("middle_name"),
            rs.getString("last_name"),
            toInstant(rs.getTimestamp("created_at")),
            toInstant(rs.getTimestamp("updated_at")),
            toInstant(rs.getTimestamp("deleted_at"))
    );

    public UserDAO(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User createUser(CreateUser user) {
        String sql = """
            INSERT INTO users (
                username,
                first_name,
                middle_name,
                last_name)
            VALUES (
                :username,
                :first_name,
                :middle_name,
                :last_name
            )
            RETURNING 
            """ + userFields;

        return jdbcTemplate.query(
                sql,
                new MapSqlParameterSource()
                        .addValue("username", user.username())
                        .addValue("first_name", user.firstName())
                        .addValue("middle_name", user.middleName())
                        .addValue("last_name", user.lastName()),
                userRowMapper
        ).get(0);
    }

    public Optional<User> deleteUser(UUID id) {
        String sql = """
            UPDATE users 
                SET 
                    updated_at = NOW(),
                    deleted_at = NOW()
                WHERE id = :id
                    AND deleted_at IS NULL
            RETURNING 
            """ + userFields;

        List<User> users = jdbcTemplate.query(
                sql,
                new MapSqlParameterSource().addValue("id", id),
                userRowMapper
        );

        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    public Optional<User> findUserById(UUID id) {
        String sql = "SELECT " + userFields +
                """
                    FROM users 
                    WHERE id = :id
                        AND deleted_at IS NULL
                """;

        List<User> users = jdbcTemplate.query(
                sql,
                new MapSqlParameterSource().addValue("id", id),
                userRowMapper
        );

        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    public Optional<User> updateUser(UUID id, UpdateUser user) {
        String sql = """
            update users SET 
                first_name = :first_name,
                middle_name = :middle_name,
                last_name = :last_name,
                updated_AT = NOW()
            WHERE id = :id
                AND deleted_at IS NULL
            RETURNING 
            """ + userFields;

        List<User> users = jdbcTemplate.query(
                sql,
                new MapSqlParameterSource()
                        .addValue("id", id)
                        .addValue("first_name", user.firstName())
                        .addValue("middle_name", user.middleName())
                        .addValue("last_name", user.lastName()),
                userRowMapper
        );

        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }
}
