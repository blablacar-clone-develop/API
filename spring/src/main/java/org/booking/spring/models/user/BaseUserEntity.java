package org.booking.spring.models.user;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
public class BaseUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        validateEmail();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        validateEmail();
    }

    private void validateEmail() {
        if (email != null && !email.matches("^[\\w-_.+]*[\\w-_.]@[\\w]+[.]([\\w]+[.])*[\\w]{2,}$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        hashPassword();
    }

    private void hashPassword() {
        if (password == null || password.length() < 6 ||
                !password.matches(".*[A-Z].*") ||
                !password.matches(".*[a-z].*") ||
                !password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Password must be at least 6 characters long, contain an uppercase letter, a lowercase letter, and a digit.");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(this.password);
    }
}
