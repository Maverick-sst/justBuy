package com.example.Backend.config.Dotenv;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

public class DotenvPropertySource implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // Load .env file from the root directory
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        
        Map<String, Object> dotenvMap = new HashMap<>();
        dotenv.entries().forEach(entry -> 
            dotenvMap.put(entry.getKey(), entry.getValue())
        );
        
        // Add these properties to Spring's environment
        applicationContext.getEnvironment()
            .getPropertySources()
            .addFirst(new MapPropertySource("dotenvProperties", dotenvMap));
    }
}