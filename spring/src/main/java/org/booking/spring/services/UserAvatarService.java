package org.booking.spring.services;

import org.booking.spring.models.user.Avatars;
import org.booking.spring.models.user.User;
import org.booking.spring.repositories.UserAvatarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAvatarService {

    @Autowired
    private UserAvatarRepository userAvatarRepository;
    @Autowired
    private UserService userService;

    public Avatars getUserAvatarById(long id) {
        return userAvatarRepository.findById(id).orElse(null);
    }

    ///Видалення аватарки по юзер айді
    public boolean deleteAvatarByUserId(Long userId) {
        int deletedRows = userAvatarRepository.deleteByUserId(userId);
        return deletedRows > 0;  // Повертає true, якщо хоча б один рядок був видалений
    }

    ///Збереження аватару користувача
    public void SaveAvatar(String filePath, Long userId) {
        User user = userService.findUserById(userId);
        Avatars avatar = new Avatars();
        avatar.setUrl(filePath);
        avatar.setUser(user);
        userAvatarRepository.save(avatar);
    }

    ///Отримати список аватарок за списком користувацький Id
    public List<Avatars> getAvatarsByUserIds(List<Long> usersIds) {
        return userAvatarRepository.findByUserIds(usersIds);
    }


}
