package org.booking.spring.controllers;


import org.booking.spring.models.auto.Autos;
import org.booking.spring.responses.DTO.AutoDto;
import org.booking.spring.services.AutosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    ///Отримати всі автомобілі яки є в базі
    @GetMapping("/getAllAutos")
    public ResponseEntity<List<AutoDto>> getAllAutos() {
        List<AutoDto> autosList = autosService.getAllAutos();
        return ResponseEntity.ok(autosList);
    }

    //Отримання автомобілів за ID користувача
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AutoDto>> getAutosByUserId(@PathVariable Long userId) {
        List<AutoDto> autos = autosService.getAutosByUserId(userId);
        return ResponseEntity.ok(autos);
    }



}
