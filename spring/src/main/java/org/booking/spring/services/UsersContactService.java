package org.booking.spring.services;

import org.booking.spring.models.user.UsersContact;
import org.booking.spring.repositories.UserRepository;
import org.booking.spring.repositories.UsersContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersContactService {
    @Autowired
    private UsersContactRepository usersContactRepository;
    public Optional<UsersContact> getByUserId(Long userId) {
        return usersContactRepository.findById(userId);
    }

    public void updateContact(UsersContact existingContact) {
        usersContactRepository.save(existingContact);
    }

    public void saveContact(UsersContact newContact) {
        usersContactRepository.save(newContact);
    }
}
