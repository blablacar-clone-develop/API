package org.booking.spring.responses.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AutoDto {
    // Getters and setters
    private Long id;
    private Long brand;
    private Long model;
    private Long color;

    public AutoDto(Long id, Long brand, Long model, Long color) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.color = color;
    }

}

