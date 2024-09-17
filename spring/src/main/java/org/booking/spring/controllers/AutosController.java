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

/*
    //Отримання автомобілів за ID користувача
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AutoDto>> getAutosByUserId(@PathVariable Long userId) {
        List<AutoDto> autos = autosService.getAutosByUserId(userId);
        return ResponseEntity.ok(autos);
    }

    //Отримання автомобіля за його ID
    @GetMapping("/getByAutoId/{autoId}")
    public ResponseEntity<AutoDto> getAutosById(@PathVariable Long autoId) {
        AutoDto auto = autosService.getAutoById(autoId);
        return ResponseEntity.ok(auto);
    }

    //Оновлення автомобіля за його ID
    @PutMapping("/update/{autoId}")
    public ResponseEntity<String> updateAuto(@PathVariable Long autoId, @RequestBody Autos auto, @RequestHeader("Authorization") String token) {
        try {
            String jwtToken = token.substring(7);  // Видаляємо "Bearer " із заголовка
            Long userId = jwtUserService.extractUserId(jwtToken);  // Витягуємо userId з токена

            // Перевіряємо, чи є користувач власником автомобіля
            if (!autosService.isUserOwnerOfAuto(autoId, userId)) {
                return ResponseEntity.status(403).body("Користувач не є власником цього автомобіля");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(403).body("Помилка авторизації");
        }

        AutoDto updatedAuto = autosService.updateAuto(autoId, auto);
        return ResponseEntity.ok("Aвто успішно змінено");
    }


    @PostMapping("/create")
    public ResponseEntity<AutoDto> createAuto(
            @RequestHeader("Authorization") String token,
            @RequestBody Autos auto
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

    //Видалити авто по Айді якщо воно нолежить юзеру
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAuto(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        try {
            String jwtToken = token.substring(7);  // Видаляємо "Bearer " із заголовка
            Long userId = jwtUserService.extractUserId(jwtToken);  // Витягуємо userId з токена

            // Перевіряємо, чи є користувач власником автомобіля
            if (!autosService.isUserOwnerOfAuto(id, userId)) {
                return ResponseEntity.status(403).body("Користувач не є власником цього автомобіля");
            }

            autosService.deleteAuto(id);
            return ResponseEntity.ok("Автомобіль успішно видалено");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    //видалити всі авто юзера
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteAllAutosByUserId(@PathVariable Long userId, @RequestHeader("Authorization") String token) {
        try {
            String jwtToken = token.substring(7);  // Видаляємо "Bearer " із заголовка
            Long userIdJwt = jwtUserService.extractUserId(jwtToken);  // Витягуємо userId з токена

            // Перевіряємо, чи є користувач власником автомобіля
            if (userIdJwt != userId) {
                return ResponseEntity.status(403).body("Користувач не є власником цих автомобілів");
            }
            autosService.deleteAllAutosByUserId(userId);
            return ResponseEntity.ok("Усі автомобілі користувача успішно видалено");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Помилка: " + ex.getMessage());
        }
    }

*/
}
