package org.example.subscriptions.controller;

import java.time.Instant;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(assignableTypes = SubscriptionController.class)
public class SubscriptionControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ProblemDetail handleDuplicateKey(ResponseStatusException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(ex.getStatusCode());
        problem.setTitle(ex.getStatusCode().toString());
        problem.setDetail(ex.getReason());
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }
}