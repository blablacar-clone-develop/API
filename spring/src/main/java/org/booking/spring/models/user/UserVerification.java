package org.booking.spring.models.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_verification")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long userId;

    @Column(nullable = false)
    private Boolean emailVerified = false;

    @Column(nullable = false)
    private Boolean phoneVerified = false;

    @Column(nullable = false)
    private Boolean documentVerified = false;

    @Column(name = "email_verification_date", nullable = true)
    private LocalDateTime emailVerificationDate;

    @Column(name = "phone_verification_date", nullable = true)
    private LocalDateTime phoneVerificationDate;

    @Column(name = "document_verification_date", nullable = true)
    private LocalDateTime documentVerificationDate;


}

