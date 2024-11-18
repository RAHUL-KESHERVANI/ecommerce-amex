package com.example.ecommerceamex.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI ecommerceAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("E-commerce API")
                        .description("E-commerce management system API")
                        .version("1.0"));
    }
}