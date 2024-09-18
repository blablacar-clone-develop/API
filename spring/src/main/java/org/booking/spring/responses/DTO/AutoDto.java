package org.booking.spring.responses.DTO;

import lombok.Getter;
import lombok.Setter;
import org.booking.spring.models.auto.Brand;
import org.booking.spring.models.auto.Color;
import org.booking.spring.models.auto.Model;

@Setter
@Getter
public class AutoDto {
    // Getters and setters
    private Long id;
    private Brand brand;
    private Model model;
    private Color color;

    public AutoDto(Long id, Brand brand, Model model, Color color) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.color = color;
    }

}

