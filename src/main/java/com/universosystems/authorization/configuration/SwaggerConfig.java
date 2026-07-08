package com.universosystems.authorization.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;

@Profile("dev")
@Configuration
public class SwaggerConfig {
    private final String port;

    public SwaggerConfig(@Value("${server.port}") String port) {
        this.port = port;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void openSwagger() {
        try {
            Thread.sleep(2000);
            new ProcessBuilder(
                    "cmd",
                    "/c",
                    "start",
                    "http://localhost:" + port + "/swagger-ui/index.html")
                    .start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
