package cn.rzpt.user.infrastructure.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class knife4jConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("基于DDD - 学生管理系统")
                        .description("基于DDD - 学生管理系统")
                        .version("1.0版本")
                        .contact(new Contact().name("MyNameWax")
                                .email("waxjava04@163.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("基于DDD - 学生管理系统")
                        .url("http://127.0.0.1:9999"));
    }
}
