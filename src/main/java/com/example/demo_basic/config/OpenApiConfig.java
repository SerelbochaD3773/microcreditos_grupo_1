package com.example.demo_basic.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

        @Bean
        public OpenAPI gestionMicrocreditosOpenAPI() {
                return new OpenAPI()
                                .info(new Info()
                                                .title("API de Gestión de Microcréditos")
                                                .description("""
                                                                Backend para un sistema simple de Gestión de Creditos.

                                                                Permite administrar **Clientes**, **Cuotas** y **Prestamos** con:
                                                                - Herencia JPA (`@MappedSuperclass`)
                                                                - Enumeraciones (`@Enumerated`)
                                                                - Auditoría automática (`@CreatedDate` / `@LastModifiedDate`)
                                                                """)
                                                .version("1.0.0")
                                                .contact(new Contact()
                                                                .name("Equipo de Desarrollo")
                                                                .email("dev@adopcion.com"))
                                                .license(new License()
                                                                .name("MIT License")
                                                                .url("https://opensource.org/licenses/MIT")));
        }
}
