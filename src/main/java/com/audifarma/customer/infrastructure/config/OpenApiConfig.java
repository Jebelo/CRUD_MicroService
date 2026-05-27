package com.audifarma.customer.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customerServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Customer Service API")
                        .version("1.0.0")
                        .description("Microservice for managing customers and their addresses. " +
                                "Built with Spring Boot 3.x and Hexagonal Architecture.")
                        .contact(new Contact()
                                .name("Audifarma")
                                .email("dev@audifarma.com.co")));
    }
}
