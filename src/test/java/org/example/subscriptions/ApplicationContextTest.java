package org.example.subscriptions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationContextTest extends AbstractIntegrationTest {
    @Autowired
    private ApplicationContext context;

    @Test
    public void shouldLoadContext() {
        assertThat(context).isNotNull();
    }

}
