package org.booking.spring.services;

import org.booking.spring.models.user.Avatars;
import org.booking.spring.repositories.UserAvatarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAvatarService {

    @Autowired
    private UserAvatarRepository userAvatarRepository;

    public Optional<Avatars> getUserAvatarById(long id) {
        return userAvatarRepository.findById(id);
    }

    ///Видалення аватарки по юзер айді
    public boolean deleteAvatarByUserId(Long userId) {
        int deletedRows = userAvatarRepository.deleteByUserId(userId);
        return deletedRows > 0;  // Повертає true, якщо хоча б один рядок був видалений
    }

    ///Збереження аватару користувача
    public void SaveAvatar(Avatars avatar) {
        userAvatarRepository.save(avatar);
    }


}
