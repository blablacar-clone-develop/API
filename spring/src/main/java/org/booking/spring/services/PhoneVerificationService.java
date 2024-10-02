package org.booking.spring.services;

import org.booking.spring.models.user.EmailVerification;
import org.booking.spring.models.user.PhoneVerification;
import org.booking.spring.repositories.PhoneVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneVerificationService {
    @Autowired
    private PhoneVerificationRepository phoneVerificationRepository;
    public PhoneVerification save(PhoneVerification phoneVerification) {
        return phoneVerificationRepository.save(phoneVerification);
    }
    public PhoneVerification verifyPhoneCode(Long userId) {
        return phoneVerificationRepository.findByUserId(userId);
    }


    public void delete(PhoneVerification codeDB) {
        phoneVerificationRepository.delete(codeDB);
    }
}