package org.example.subscriptions.client.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "org.example.subscriptions.client")
public class FeignClientsConfiguration { }
