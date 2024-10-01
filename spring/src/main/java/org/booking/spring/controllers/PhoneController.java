package org.booking.spring.controllers;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.booking.spring.services.SmsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;

@RestController
@RequestMapping("/api")
public class PhoneController {
    private final SmsService smsService;
    public PhoneController(SmsService smsService) {
        this.smsService = smsService;
    }
    @PostMapping("/sendCode")
    public ResponseEntity<String> sendCode(@RequestBody String phoneNumber) {
        String verificationCode = generateCode();
        boolean isSent = smsService.sendVerificationCode(phoneNumber, verificationCode);

        if (isSent) {
            return ResponseEntity.ok("sent");
        } else {
            return ResponseEntity.status(500).body("failed");
        }
    }
    private String generateCode() {
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(999999);
        return String.format("%06d", num);
    }
}
