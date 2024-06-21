package cn.rzpt;

import cn.rzpt.user.infrastructure.config.CanalConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class DddDemoApplication implements CommandLineRunner {

    private final CanalConfig canalConfig;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplicationBuilder(DddDemoApplication.class).build(args);
        Environment env = app.run(args).getEnvironment();
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        log.info("--/\n---------------------------------------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\t{}://localhost:{}\n\t" +
                        "Swagger: \t\t{}://localhost:{}/{}\n\t" +
                        "Profile(s): \t{}" +
                        "\n---------------------------------------------------------------------------------------",
                env.getProperty("spring.application.name"),
                protocol,
                env.getProperty("server.port"),
                protocol,
                env.getProperty("server.port"),
                "doc.html",
                env.getActiveProfiles());
    }


    @Override
    public void run(String... args) throws Exception {
        canalConfig.run();
    }
}
