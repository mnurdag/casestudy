package com.zad.accountservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Casestudy API")
                        .version("1.0")
                        .description("""
                                This API has several features.
                                First one is that you can create seperate accounts based on different currencies.
                                Second one ist that you can withdraw, deposit or send money from your accounts.
                                You can also retrieve the account balance.
                                """)
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")
                        )
                        .contact(new Contact()
                                .email("muhammednur.nurdag@gmail.com")
                                .name("Muhammed Nur Nurdağ")
                        )
                );
    }
}
