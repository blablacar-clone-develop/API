package org.booking.spring.controllers;

import org.booking.spring.models.user.EmailVerification;
import org.booking.spring.models.user.PhoneVerification;
import org.booking.spring.models.user.UsersContact;
import org.booking.spring.services.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PhoneController {
    private final SmsService smsService;
    private final JwtUserService jwtUserService;
    private final PhoneVerificationService phoneVerificationService;
    private final UserVerificationService userVerificationService;
    private final UsersContactService usersContactService;
    public PhoneController(SmsService smsService, JwtUserService jwtUserService, PhoneVerificationService phoneVerificationService, UserVerificationService userVerificationService, UsersContactService usersContactService) {
        this.smsService = smsService;
        this.jwtUserService = jwtUserService;
        this.phoneVerificationService = phoneVerificationService;
        this.userVerificationService = userVerificationService;
        this.usersContactService = usersContactService;
    }
    @PostMapping("/phone/verifyCode")
    public ResponseEntity<String> verifyCode(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody Map<String, String> requestBody) {


        String code = requestBody.get("code");
        String phone = requestBody.get("phone");
        String countryCode = requestBody.get("countryCode");
        phone = "+" + phone;
        Long userId = Long.valueOf(requestBody.get("userId"));
        PhoneVerification codeDB = phoneVerificationService.verifyPhoneCode(userId);
        if(!codeDB.isExpired())
        {
            if(code.equals(codeDB.getVerificationCode()))
            {
                phoneVerificationService.delete(codeDB);
                Optional<UsersContact> usersContact = usersContactService.getByUserId(userId);
                if(usersContact.isPresent()){
                    UsersContact u = usersContact.get();
                    u.setPhoneNumber(phone);
                    usersContactService.updateContact(u);
                }
                else {
                    UsersContact  u = new UsersContact();
                    u.setUserId(userId);
                    u.setCountryCode(countryCode);
                    u.setPhoneNumber(phone);
                    usersContactService.saveContact(u);
                }
                userVerificationService.verifyPhoneUserById(userId);

                return ResponseEntity.ok("code");
            }
            else
                return ResponseEntity.ok("incorrect");
        }
        else {
            return ResponseEntity.ok("time");
        }

    }
    @PostMapping("/sendCode")
    public ResponseEntity<String> sendCode(@RequestHeader("Authorization") String authorizationHeader,
                                           @RequestBody Map<String, String> requestBody) {
        String verificationCode = generateCode();
        String jwtToken = authorizationHeader.substring(7);
        Long userId = jwtUserService.extractUserId(jwtToken);
        String phone = requestBody.get("phone");
        phone = "+" + phone;
        boolean isSent = smsService.sendVerificationCode(phone, verificationCode);
        if (isSent) {
            PhoneVerification phoneVerification = new PhoneVerification();
            phoneVerification.setVerificationCode(verificationCode);
            phoneVerification.setExpiresAt(LocalDateTime.now().plusMinutes(5));
            phoneVerification.setUserId(userId);
            phoneVerificationService.save(phoneVerification);
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
