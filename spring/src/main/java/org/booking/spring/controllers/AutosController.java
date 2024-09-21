package org.booking.spring.controllers;


import org.booking.spring.models.auto.Autos;
import org.booking.spring.models.auto.Brand;
import org.booking.spring.models.auto.Color;
import org.booking.spring.models.auto.Model;
import org.booking.spring.responses.DTO.AutoDto;
import org.booking.spring.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/autos")
public class AutosController {
    private final AutosService autosService;
    private final BrandsService  brandsService;
    private final ModelsService modelsService;
    private final ColorService colorService;
    @Autowired
    public AutosController(AutosService autosService, BrandsService brandsService, ModelsService modelsService, ColorService colorService) {
        this.autosService = autosService;
        this.brandsService = brandsService;
        this.modelsService = modelsService;
        this.colorService = colorService;
    }
    @Autowired
    private JwtUserService jwtUserService;

    @PutMapping("/create")
    public ResponseEntity<AutoDto> createAuto(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, Object> autoData
    ) {
        try {
            String jwtToken = token.substring(7);  // Видаляємо "Bearer " із заголовка
            Long userId = jwtUserService.extractUserId(jwtToken);  // Витягуємо userId з токена

            // Отримуємо назви бренду та моделі з переданих даних
            String brandName = (String) autoData.get("brand");
            String modelName = (String) autoData.get("model");
            Optional<Brand> brandOp = brandsService.findByName(brandName);

            if (brandOp.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            Brand brand = brandOp.get();
            Optional<Model> modelOp = modelsService.findByNameAndBrandId(modelName, brand.getId());
            if (modelOp.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            Autos auto = new Autos();
            auto.setBrand(brand);
            auto.setModel(modelOp.get());
            Integer idInteger = (Integer) autoData.get("id");
            Long idLong = idInteger.longValue();

            auto.setColor(colorService.findById(idLong).get());

            Autos createdAuto = autosService.createAutoForUser(userId, auto);

            return ResponseEntity.ok(autosService.returnAutoDto(createdAuto));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);  // Обробляємо помилки
        }
    }

    //Отримання автомобіля за його ID
    @GetMapping("/getByAutoId/{autoId}")
    public ResponseEntity<AutoDto> getAutosById(
            @RequestHeader("Authorization") String token,
            @PathVariable Long autoId
    ) {
        try {
            AutoDto auto = autosService.getAutoById(autoId);
            return ResponseEntity.ok(auto);

        } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);  // Обробляємо помилки
        }
    }

    //Оновлення автомобіля за його ID
    @PutMapping("/update/{autoId}")
    public ResponseEntity<String> updateAuto(
            @PathVariable Long autoId,
            @RequestBody Map<String, Object> autoData,
            @RequestHeader("Authorization") String token
    ) {
        try {
            String jwtToken = token.substring(7);  // Видаляємо "Bearer " із заголовка
            Long userId = jwtUserService.extractUserId(jwtToken);  // Витягуємо userId з токена

            // Перевіряємо, чи є користувач власником автомобіля
            if (!autosService.isUserOwnerOfAuto(autoId, userId)) {
                return ResponseEntity.status(403).body("Користувач не є власником цього автомобіля");
            }

            // Отримуємо назви бренду та моделі з переданих даних
            String brandName = (String) autoData.get("brand");
            String modelName = (String) autoData.get("model");
            Optional<Brand> brandOp = brandsService.findByName(brandName);

            if (brandOp.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            Brand brand = brandOp.get();
            Optional<Model> modelOp = modelsService.findByNameAndBrandId(modelName, brand.getId());

            if (modelOp.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            Autos auto = new Autos();
            auto.setBrand(brand);
            auto.setModel(modelOp.get());
            Integer idInteger = (Integer) autoData.get("id");
            Long idLong = idInteger.longValue();

            auto.setColor(colorService.findById(idLong).get());
            autosService.updateAuto(userId, auto);
        } catch (Exception ex) {
            return ResponseEntity.status(403).body("Помилка авторизації");
        }

        return ResponseEntity.ok("Aвто успішно змінено");
    }



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
