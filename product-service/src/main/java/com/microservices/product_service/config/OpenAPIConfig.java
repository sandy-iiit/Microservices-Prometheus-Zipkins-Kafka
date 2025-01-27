package com.microservices.product_service.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI productServiceAPI() {
        return new OpenAPI()
                .info(new Info().title("Product Service API")
                        .description("This is api for product service!")
                        .version("1.0.0")
                        .license(new License().name("Apache")))
                        .externalDocs(new ExternalDocumentation()
                                .description("Refer these for more information.")
                                .url("https://github.com/microservices/product-service"));
    }
}
