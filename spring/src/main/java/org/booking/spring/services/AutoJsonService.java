package org.booking.spring.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.booking.spring.responses.DTO.AutoDtoJson;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
@Service
public class AutoJsonService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<AutoDtoJson> loadCarDataFromJson(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), new TypeReference<List<AutoDtoJson>>() {});
    }
}
