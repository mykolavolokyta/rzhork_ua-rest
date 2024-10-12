package bebra.rzhork_ua_rest.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("rzhork.ua API"))
                .addSecurityItem(new SecurityRequirement().addList("RzhorkSecurityScheme"))
                .components(new Components().addSecuritySchemes("RzhorkSecurityScheme", new SecurityScheme()
                        .name("RzhorkSecurityScheme").type(SecurityScheme.Type.HTTP).scheme("basic")));
    }
}
