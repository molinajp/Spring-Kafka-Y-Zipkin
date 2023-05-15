package com.example.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String TAG_NAME = "Template";
    private static final String TAG_DESCRIPTION = "All resources available for example microservice";
    private static final String TAG_VERSION = "2.0";

    @Bean
    public OpenAPI getApiInfo() {
        return new OpenAPI().info(new Info()
                .title(TAG_NAME)
                .description(TAG_DESCRIPTION)
                .version(TAG_VERSION));
    }


}
