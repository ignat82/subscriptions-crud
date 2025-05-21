package org.example.subscriptions.client.configuration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@ConditionalOnClass(FeignClient.class)
@Import(FeignClientProperties.FeignClientConfiguration.class)
public class FeignClientsAutoconfiguration { }