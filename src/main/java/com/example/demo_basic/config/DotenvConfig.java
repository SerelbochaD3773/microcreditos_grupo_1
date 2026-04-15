package com.example.demo_basic.config;

import org.springframework.context.annotation.Configuration;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class DotenvConfig {
    static {
        loadEnvFile();
    }

    private static void loadEnvFile() {
        try {
            String envPath = ".env";
            if (Files.exists(Paths.get(envPath))) {
                try (BufferedReader reader = new BufferedReader(new FileReader(envPath))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.trim().isEmpty() || line.trim().startsWith("#")) {
                            continue;
                        }
                        String[] parts = line.split("=", 2);
                        if (parts.length == 2) {
                            String key = parts[0].trim();
                            String value = parts[1].trim();
                            System.setProperty(key, value);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("No se pudo cargar el archivo .env: " + e.getMessage());
        }
    }
}
