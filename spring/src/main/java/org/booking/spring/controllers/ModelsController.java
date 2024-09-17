package org.booking.spring.controllers;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.booking.spring.requests.auth.BrandRequest;
import org.booking.spring.services.BrandsService;
import org.booking.spring.services.ModelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/autos/models")
public class ModelsController {
    private final ModelsService modelsService;
    private final BrandsService brandsService;
    @Autowired
    public ModelsController(ModelsService modelsService, BrandsService brandsService) {
        this.modelsService = modelsService;
        this.brandsService = brandsService;
    }
    @GetMapping("/all/{brand}")
    public ResponseEntity<List<String>> getAllAutosName(@PathVariable String brand) {
        try {
            Long brandId = brandsService.getBrandIdByName(brand);
            List<String> models = modelsService.getModelsByBrandId(brandId);
            return ResponseEntity.ok(models);
        } catch (Exception ex)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }
}
