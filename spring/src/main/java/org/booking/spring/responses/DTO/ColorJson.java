package org.booking.spring.responses.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.booking.spring.models.auto.Color;

import java.util.List;

@Getter
@Setter
public class ColorJson {
    @JsonProperty("colors")
    private List<Color> colors;

}
