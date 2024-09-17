package org.booking.spring.json;

import org.booking.spring.responses.DTO.AutoDtoJson;
import org.booking.spring.services.AutoJsonService;
import org.booking.spring.services.AutosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class AutoDataInitializer implements CommandLineRunner {

    @Autowired
    private AutoJsonService autoDataService;

    @Autowired
    private AutosService autosService;

    @Override
    public void run(String... args) throws Exception {

        String jsonFilePath = "src/main/java/org/booking/spring/json/cars.json";

        try {
            List<AutoDtoJson> autoDtoJsonList = autoDataService.loadCarDataFromJson(jsonFilePath);
            autosService.saveFileInDB(autoDtoJsonList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
