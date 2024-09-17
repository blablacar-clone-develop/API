package org.booking.spring.responses.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AutoDtoJson {
    private String name;
    private List<AutoModelDTO> models;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AutoModelDTO {
        private String name;
    }
}
