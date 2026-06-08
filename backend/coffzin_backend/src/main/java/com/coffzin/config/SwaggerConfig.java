package com.coffzin.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    public static final String COOKIE_AUTH = "cookieAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Coffzin Backend API")
                        .version("1.0.0")
                        .description("""
                                Documentacao completa da API do Coffzin.

                                Fluxo de autenticacao no Swagger:
                                1. Execute POST /api/users para criar uma conta.
                                2. Execute POST /api/auth/login com email e senha.
                                3. O backend grava o JWT no cookie HttpOnly chamado token.
                                4. Depois disso, execute endpoints protegidos como /api/users, /api/users/me e operacoes de escrita em produtos.
                                """)
                        .contact(new Contact()
                                .name("Coffzin")
                                .email("support@coffzin.local"))
                        .license(new License()
                                .name("Internal project")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local backend")
                ))
                .components(new Components()
                        .addSecuritySchemes(COOKIE_AUTH, new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.COOKIE)
                                .name("token")
                                .description("JWT salvo automaticamente pelo endpoint /api/auth/login no cookie HttpOnly token.")));
    }
}
