package org.booking.spring.controllers;


import lombok.RequiredArgsConstructor;
import org.booking.spring.services.StorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {

    private final StorageService storageService;


    @PostMapping("/upload/avatar")
    public ResponseEntity<String> save(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
//        storageService.disk(DriverEnum.Local).put("files", fileName, file);
//        storageService.disk(DriverEnum.MinIo).put("files", fileName, file);

        String filePath = storageService.put("files", fileName, file);

        return ResponseEntity.ok(filePath);
    }

}

