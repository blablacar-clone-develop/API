package org.booking.spring.json;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.booking.spring.repositories.ColorRepository;
import org.booking.spring.responses.DTO.ColorJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
@Configuration
public class ColorDataInitializer {

    @Autowired
    private ColorRepository colorRepository;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            try {
                if (colorRepository.count() == 0) {
                    byte[] jsonData = Files.readAllBytes(Paths.get("src/main/java/org/booking/spring/json/colors.json"));
                    ObjectMapper objectMapper = new ObjectMapper();
                    ColorJson colorJson = objectMapper.readValue(jsonData, ColorJson.class);
                    colorRepository.saveAll(colorJson.getColors());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}