package org.booking.spring.controllers;


import org.booking.spring.models.auto.Autos;
import org.booking.spring.responses.DTO.AutoDto;
import org.booking.spring.services.AutosService;
import org.booking.spring.services.JwtUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autos")
public class AutosController {
    private final AutosService autosService;

    @Autowired
    public AutosController(AutosService autosService) {
        this.autosService = autosService;
    }
    @Autowired
    private JwtUserService jwtUserService;


    ///Отримати всі автомобілі яки є в базі в разі якщо авторизований
    @GetMapping("/getAllAutos")
    public ResponseEntity<List<AutoDto>> getAllAutos(@RequestHeader("Authorization") String token
    ) {
        try {
            String jwtToken = token.substring(7);  // Видаляємо "Bearer " із заголовка
            Long userId = jwtUserService.extractUserId(jwtToken);  // Витягуємо userId з токена
            System.out.println("userId  "+userId);
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

        List<AutoDto> autosList = autosService.getAllAutos();
        return ResponseEntity.ok(autosList);
    }

    //Отримання автомобілів за ID користувача
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AutoDto>> getAutosByUserId(@PathVariable Long userId) {
        List<AutoDto> autos = autosService.getAutosByUserId(userId);
        return ResponseEntity.ok(autos);
    }

    //Отримання автомобіля за його ID
    @GetMapping("/getByAutoId/{autoId}")
    public ResponseEntity<AutoDto> getAutosById(@PathVariable Long autoId) {
        System.out.println(autoId);
        AutoDto auto = autosService.getAutoById(autoId);
        return ResponseEntity.ok(auto);
    }


    @PostMapping("/create")
    public ResponseEntity<AutoDto> createAuto(
            @RequestBody Autos auto,
            @RequestHeader("Authorization") String token
    ) {
        try {
            String jwtToken = token.substring(7);  // Видаляємо "Bearer " із заголовка
            Long userId = jwtUserService.extractUserId(jwtToken);  // Витягуємо userId з токена

            Autos createdAuto = autosService.createAutoForUser(userId, auto);  // Створюємо автомобіль для користувача
            return ResponseEntity.ok(autosService.returnAutoDto(createdAuto));  // Повертаємо відповідь із створеним авто у вігляді ДТО

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);  // Обробляємо помилки
        }
    }

}
