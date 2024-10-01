package org.booking.spring.services;

import org.booking.spring.models.user.EmailVerification;
import org.booking.spring.repositories.EmailVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailVerificationService {
    @Autowired
    private EmailVerificationRepository emailVerificationRepository;
    public EmailVerification save(EmailVerification emailVerification) {
        return emailVerificationRepository.save(emailVerification);
    }

    public EmailVerification verifyEmailCode(Long userId) {
        return emailVerificationRepository.findByUserId(userId);
    }


}
