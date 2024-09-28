package org.booking.spring.services;

import org.booking.spring.models.user.UserVerification;
import org.booking.spring.repositories.UserVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserVerificationService {
    @Autowired
    private UserVerificationRepository userVerificationRepository;

    public void addInfoAboutNewUserById(Long id) {
        UserVerification userVerification = new UserVerification();
        userVerification.setUserId(id);
        userVerificationRepository.save(userVerification);
    }

    public boolean verifyEmailUserById(Long userId) {
        UserVerification userVerification = userVerificationRepository.findByUserId(userId);
        if(userVerification != null) {
            userVerification.setEmailVerified(true);
            return true;
        } else {
            return false;
        }
    }

}
