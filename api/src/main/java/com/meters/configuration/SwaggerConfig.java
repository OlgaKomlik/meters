package com.meters.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customerOpenAPI() {
        return new OpenAPI().info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .contact(contact())
                .title("Meters real estate agency DEMO")
                .description("This Demo project show author skills")
                .version("1.0")
                .license(apiLicence());
    }

    private License apiLicence() {
        return new License()
                .name("MIT Licence")
                .url("https://opensource.org/licenses/mit-licenses.php");
    }

    private Contact contact() {
        return new Contact()
                .email("oluska92666@gmail.com")
                .name("Olga Komlik")
                .url("https://meters.com");
    }
}
