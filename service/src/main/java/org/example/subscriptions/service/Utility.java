package org.example.subscriptions.service;

import java.sql.Timestamp;
import java.time.Instant;

public class Utility {
    public static Instant toInstant(Timestamp timestamp) {
        return timestamp != null ? timestamp.toInstant() : null;
    }
}
