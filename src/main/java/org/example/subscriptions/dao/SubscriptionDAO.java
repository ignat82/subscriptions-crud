package org.example.subscriptions.dao;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.example.subscriptions.model.suscription.CreateSubscription;
import org.example.subscriptions.model.suscription.Subscription;
import org.example.subscriptions.model.suscription.SubscriptionStatistic;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import static org.example.subscriptions.Utility.toInstant;

@Repository
public class SubscriptionDAO {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final String subscriptionFields = """
        subscriptions.id,
        subscriptions.title,
        subscriptions.description,
        subscriptions.start_date,
        subscriptions.end_date,
        subscriptions.user_id,
        subscriptions.created_at,
        subscriptions.updated_at,
        subscriptions.deleted_at
        """;

    private final RowMapper<Subscription> subscriptionRowMapper = (rs, rowNum) ->
            new Subscription(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getTimestamp("start_date").toInstant(),
                    rs.getTimestamp("end_date").toInstant(),
                    UUID.fromString(rs.getString("user_id")),
                    toInstant(rs.getTimestamp("created_at")),
                    toInstant(rs.getTimestamp("updated_at")),
                    toInstant(rs.getTimestamp("deleted_at"))
            );

    public SubscriptionDAO(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Subscription> createSubscription(UUID userId, CreateSubscription subscription) {
        String sql = """
            INSERT INTO subscriptions (
                title,
                description,
                start_date,
                end_date,
                user_id
            ) 
            SELECT
                :title,
                :description,
                :start_date,
                :end_date,
                :user_id
                FROM users
                WHERE id = :user_id
                    AND deleted_at IS NULL
            RETURNING 
            """ + subscriptionFields;

        List<Subscription> subscriptions =  jdbcTemplate.query(
                sql,
                new MapSqlParameterSource()
                        .addValue("title", subscription.title())
                        .addValue("description", subscription.description())
                        .addValue("start_date", subscription.startDate().atOffset(ZoneOffset.UTC))
                        .addValue("end_date", subscription.endDate().atOffset(ZoneOffset.UTC))
                        .addValue("user_id", userId),
                subscriptionRowMapper
        );

        return subscriptions.isEmpty() ? Optional.empty() : Optional.of(subscriptions.get(0));
    }

    public Optional<Subscription> deleteSubscription(UUID id) {
        String sql = """
            UPDATE subscriptions 
                SET 
                    updated_at = NOW(),
                    deleted_at = NOW()
                WHERE id = :id
                    AND deleted_at IS NULL
            RETURNING 
            """ + subscriptionFields;

        List<Subscription> subscriptions = jdbcTemplate.query(
                sql,
                new MapSqlParameterSource().addValue("id", id),
                subscriptionRowMapper
        );

        return subscriptions.isEmpty() ? Optional.empty() : Optional.of(subscriptions.get(0));
    }

    public Optional<List<Subscription>> findByUserId(UUID userId) {
        String sql = "SELECT " + subscriptionFields + """
            FROM subscriptions
                JOIN users u ON user_id = u.id
            WHERE u.id = :user_id
                AND u.deleted_at IS NULL
                AND subscriptions.deleted_at IS NULL
            """;

        List<Subscription> subscriptions =  jdbcTemplate.query(
                sql,
                new MapSqlParameterSource()
                        .addValue("user_id", userId),
                subscriptionRowMapper
        );

        return subscriptions.isEmpty() ? Optional.empty() : Optional.of(subscriptions);
    }

    public List<SubscriptionStatistic> findMostPopular(int limit) {
        String sql =
            """
                SELECT title, COUNT(*) AS subscribers_count
                FROM subscriptions
                    WHERE deleted_at IS NULL
                    GROUP BY title
                ORDER BY subscribers_count DESC, title
                LIMIT :limit
            """;

        List<SubscriptionStatistic> subscriptionsStatistic =  jdbcTemplate.query(
                sql,
                new MapSqlParameterSource()
                        .addValue("limit", limit),
                (rs, rowNum) -> new SubscriptionStatistic(
                        rs.getString("title"),
                        rs.getInt("subscribers_count")
                )
        );

        return subscriptionsStatistic;
    }
}