package org.booking.spring.controllers;

import org.booking.spring.services.AutosService;
import org.booking.spring.services.BrandsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/autos/brands")
public class BrandsController {
    private final BrandsService brandsService;

    @Autowired
    public BrandsController(BrandsService brandsService) {
        this.brandsService = brandsService;
    }
    @GetMapping("/all")
    public ResponseEntity<List<String>> getAllAutosName() {
        try {
            List<String> autosList = brandsService.getAllAutosBrand();
            return ResponseEntity.ok(autosList);
        } catch (Exception ex)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }
    @GetMapping("/top")
    public ResponseEntity<List<String>> getTopAutosName() {
        try {
            List<String> autosList = brandsService.getTop5Brands();
            return ResponseEntity.ok(autosList);
        } catch (Exception ex)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }
}
