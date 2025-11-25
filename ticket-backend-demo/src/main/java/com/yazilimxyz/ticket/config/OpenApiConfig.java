package com.yazilimxyz.ticket.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ticket Sistemi API")
                        .version("1.0")
                        .description("Bu proje Spring Boot ile geliştirilmiş Ticket/Admin panel backendidir."));
    }
}