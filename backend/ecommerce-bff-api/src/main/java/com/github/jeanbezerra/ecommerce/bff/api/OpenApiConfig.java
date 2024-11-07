package com.github.jeanbezerra.ecommerce.bff.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {
	
	
	@Bean
	OpenAPI customConfiguration() {
		return new OpenAPI().components(new Components())
				.info(new Info().title("PIT em Engenharia de Software").description("Este projeto faz parte da disciplina Projeto Integrador Transdisciplinar do curso de Engenharia de Software. O objetivo principal é aplicar os conhecimentos adquiridos ao longo do curso para desenvolver uma solução completa de software, incluindo o front-end, back-end e banco de dados.")
						.version("1.0.0-RELEASE")
						.license(new License().name("Jean Carlos Bezerra Macena da Silva").url("https://github.com/jeanbezerra"))
						.termsOfService("https://github.com/jeanbezerra"))
						.addSecurityItem(new SecurityRequirement()
						.addList("Bearer Authentication"));
				//.components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()));
	}
	
	private SecurityScheme createAPIKeyScheme() {
	    return new SecurityScheme().type(SecurityScheme.Type.HTTP)
	        .bearerFormat("JWT")
	        .scheme("bearer");
	}

    @Bean
    InternalResourceViewResolver defaultViewResolver() {
		return new InternalResourceViewResolver();
	}

}