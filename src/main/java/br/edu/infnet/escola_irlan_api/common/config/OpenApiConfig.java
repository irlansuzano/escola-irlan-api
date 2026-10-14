package br.edu.infnet.escola_irlan_api.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Escola Irlan API")
                        .version("1.0.0")
                        .description("API para gerenciamento de instituição de ensino e biblioteca."));
    }
}
