package org.booking.spring.controllers;


import org.booking.spring.models.auto.Autos;
import org.booking.spring.services.AutosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/autos")
public class AutosController {
    private final AutosService autosService;

    @Autowired
    public AutosController(AutosService autosService) {
        this.autosService = autosService;
    }

    @GetMapping("/getAllAutos")
    public ResponseEntity<List<Autos>> getAllAutos() {
        List<Autos> autosList = autosService.getAllAutos();
        return ResponseEntity.ok(autosList);
    }


}
