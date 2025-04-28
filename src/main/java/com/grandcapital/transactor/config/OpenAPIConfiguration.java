package com.grandcapital.transactor.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {
    @Bean
    public OpenAPI myOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Money transfer")
                        .version("1.0")
                        .description("Test project for the money transfer"));
    }
}
