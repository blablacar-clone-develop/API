package org.booking.spring.responses.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AutoDto {
    // Getters and setters
    private Long id;
    private String brand;
    private String model;
    private String color;

    public AutoDto(Long id, String brand, String model, String color) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.color = color;
    }

}

