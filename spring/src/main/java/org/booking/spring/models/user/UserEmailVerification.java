package org.booking.spring.models.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_email_verification")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEmailVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long userId;

    @Column(name = "verification_code",nullable = false)
    private String verificationCode;

    @Column(name = "time_code_generation", nullable = true)
    private LocalDateTime timeCodeGeneration;

    // Сеттер з автоматичним оновленням дати
    public void setVerificationCode(String code) {
        if (code != null) {
            verificationCode = code;
            timeCodeGeneration = LocalDateTime.now();
        }
    }
}
