package co.zw.celfin.healthyliving.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI healthyLivingOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Healthy Living API")
                        .description("Meal plans, workout schedule and monthly grocery list for the Healthy Living app")
                        .version("v0.1"));
    }
}
