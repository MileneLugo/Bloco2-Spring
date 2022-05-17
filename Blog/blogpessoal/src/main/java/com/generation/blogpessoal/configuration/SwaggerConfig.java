package com.generation.blogpessoal.configuration;

import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.responses.ApiResponse; 
import io.swagger.v3.oas.models.responses.ApiResponses; 
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;


@Configuration
public class SwaggerConfig {

	//Ctrl O para importar
	//Informações pessoais
	@Bean
	public OpenAPI springBlogPessoalOpenAPI() {
		
        return new OpenAPI()
				.info(new Info()
					.title("Projeto Blog Pessoal")
					.description("Projeto Blog Pessoal - Generation Brasil")
					.version("v0.0.1") // Versão da aplicação, atualizando cada vez que mudar
				.license(new License()
					.name("Generation Brasil")
					.url("https://brazil.generation.org/")) // Informações da empresa
				.contact(new Contact() // Informações de contato / repositório do projeto
					.name("Repositório do Projeto do Blog Pessoal")
					.url("https://github.com/MileneLugo/Bloco2-Spring/tree/main/Blog")
					.email("milenemontans@outlook.com")))
				.externalDocs(new ExternalDocumentation()
					.description("Meu GitHub")
					.url("https://github.com/MileneLugo"));
	}

	// Traduz respostas 'http'
	@Bean
	public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() {

		return openApi -> {
			openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {

				ApiResponses apiResponses = operation.getResponses();

				apiResponses.addApiResponse("200", createApiResponse("Sucesso!"));
				apiResponses.addApiResponse("201", createApiResponse("Objeto Persistido!"));
				apiResponses.addApiResponse("204", createApiResponse("Objeto Excluído!"));
				apiResponses.addApiResponse("400", createApiResponse("Erro na Requisição!"));
				apiResponses.addApiResponse("401", createApiResponse("Acesso Não Autorizado!"));
				apiResponses.addApiResponse("404", createApiResponse("Objeto Não Encontrado!"));
				apiResponses.addApiResponse("500", createApiResponse("Erro na Aplicação!"));

			}));
		};
	}
	
	private ApiResponse createApiResponse(String message) {
		return new ApiResponse().description(message);
	}
}
	

