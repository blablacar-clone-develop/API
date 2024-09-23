package org.booking.spring.controllers;


import lombok.RequiredArgsConstructor;
import org.booking.spring.services.JwtUserService;
import org.booking.spring.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {
    @Autowired
    private JwtUserService jwtUserService;
    private final StorageService storageService;

    // Дозволені типи файлів для завантаження для аватару
    private static final List<String> ALLOWED_CONTENT_TYPES_FOR_AVATAR = Arrays.asList(
            "image/jpeg",
            "image/png"
    );

    @PostMapping("/upload/avatar")
    public ResponseEntity<String> save(
            @RequestHeader("Authorization") String token,
            @RequestParam("avatar") MultipartFile file
    ) {
        try {
            String jwtToken = token.substring(7);  // Видаляємо "Bearer " із заголовка
            Long userId = jwtUserService.extractUserId(jwtToken);  // Витягуємо userId з токена

            if (userId == null) {
                return ResponseEntity.status(403).body("Authorization error, please login and try again");
            }

            // Перевірка типу файлу
            String contentType = file.getContentType();
            if (contentType == null || !ALLOWED_CONTENT_TYPES_FOR_AVATAR.contains(contentType)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Недозволений тип файлу. Дозволені типи: JPEG, PNG, GIF.");
            }

            // Отримуємо поточну дату і час
            LocalDateTime now = LocalDateTime.now();

            // Форматуємо дату і час у потрібний формат
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

            String fileName = userId + "_" + now.format(formatter) + "." + file.getContentType().split("/")[1];

            // Викликаємо метод збереження файлу
            String filePath = storageService.put("avatar", fileName, file);

            return ResponseEntity.ok(filePath);
        } catch (Exception ex) {
            // Повертаємо статус 400 Bad Request при помилках
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}

