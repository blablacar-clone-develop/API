package org.booking.spring.controllers;


import lombok.RequiredArgsConstructor;
import org.booking.spring.services.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {

    private final StorageService storageService;

    // Дозволені типи файлів для завантаження для аватару
    private static final List<String> ALLOWED_CONTENT_TYPES_FOR_AVATAR = Arrays.asList(
            "image/jpeg",
            "image/png"
    );

    @PostMapping("/upload/avatar")
    public ResponseEntity<String> save(@RequestParam("avatar") MultipartFile file) {
        try {
            // Перевірка типу файлу
            String contentType = file.getContentType();
            if (contentType == null || !ALLOWED_CONTENT_TYPES_FOR_AVATAR.contains(contentType)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Недозволений тип файлу. Дозволені типи: JPEG, PNG, GIF.");
            }

            String fileName = file.getOriginalFilename();
            // Викликаємо метод збереження файлу
            String filePath = storageService.put("avatar", fileName, file);

            return ResponseEntity.ok(filePath);
        } catch (Exception ex) {
            // Повертаємо статус 400 Bad Request при помилках
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}

