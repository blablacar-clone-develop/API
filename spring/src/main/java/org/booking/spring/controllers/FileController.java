package org.booking.spring.controllers;


import lombok.RequiredArgsConstructor;
import org.booking.spring.models.user.Avatars;
import org.booking.spring.services.JwtUserService;
import org.booking.spring.services.StorageService;
import org.booking.spring.services.UserAvatarService;
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

    private final UserAvatarService userAvatarService;

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

            String[] adressArr = filePath.split("9000");

            if(adressArr[0].equals("blablacar.imagestorage.minio")) {
                filePath = "/storage"+ adressArr[1];
            }

            userAvatarService.SaveAvatar(filePath, fileName,userId);

            return ResponseEntity.ok("Аватар успішно завантажено");
        } catch (Exception ex) {
            // Повертаємо статус 400 Bad Request при помилках
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("/delete/avatar")
    public ResponseEntity<String> deleteAvatar(
            @RequestHeader("Authorization") String token
    ){
        try {
            String jwtToken = token.substring(7);  // Видаляємо "Bearer " із заголовка
            Long userId = jwtUserService.extractUserId(jwtToken);  // Витягуємо userId з токена

            if (userId == null) {
                return ResponseEntity.status(403).body("Authorization error, please login and try again");
            }




            return ResponseEntity.ok("Видалено успішно");
        }catch (Exception ex) {
        // Повертаємо статус 400 Bad Request при помилках
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }


    /**
     * @param token Beurer token
     * @return String URL to user avatar
     */
    @GetMapping("/avatar")
    public String getUrlAvatar(
            @RequestHeader("Authorization") String token
    ) {
        try{
            String jwtToken = token.substring(7);  // Видаляємо "Bearer " із заголовка
            Long userId = jwtUserService.extractUserId(jwtToken);  // Витягуємо userId з токена

            if (userId == null) {
                return "Authorization error, please login and try again";
            }

            return userAvatarService.getUserAvatarById(userId).getUrl();

        } catch (Exception e) {
            return "ERROR";
        }
    }

}

