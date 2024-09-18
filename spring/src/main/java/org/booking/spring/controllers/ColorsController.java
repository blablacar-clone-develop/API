package org.booking.spring.controllers;

import org.booking.spring.models.auto.Color;
import org.booking.spring.services.ColorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/autos/colors")
public class ColorsController {
    private final ColorService colorService;

    public ColorsController(ColorService colorService) {
        this.colorService = colorService;
    }

    @GetMapping("/all")
    private ResponseEntity<List<Color>> gelAllColors()
    {
        try {
            List<Color> allColors = colorService.getAllColors();
            return ResponseEntity.ok(allColors);
        } catch (Exception ex)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
