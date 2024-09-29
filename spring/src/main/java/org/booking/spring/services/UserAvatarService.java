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
    @Autowired
    private StorageService storageService;


    public Avatars getUserAvatarById(long id) {
        return userAvatarRepository.findByUserId(id);
    }

    public boolean deleteUserAvatarFormStorageByUserId(long userid) {
        Avatars avatars = userAvatarRepository.findByUserId(userid);
        return storageService.delete("avatar", avatars.getFilename());
    }

    ///Видалення аватарки по юзер айді filestorage & DB
    public boolean deleteAvatarByUserId(Long userId) {
        Avatars avatars = userAvatarRepository.findByUserId(userId);
        if (avatars == null) {return false;}
        boolean isDeleted = storageService.delete("avatar", avatars.getFilename());
        int deletedRows = userAvatarRepository.deleteByUserId(userId);
        return deletedRows > 0 && isDeleted;
    }

    ///Збереження аватару користувача
    public void SaveAvatar(String filePath, String fileName,Long userId) {
        User user = userService.findUserById(userId);
        Avatars avatar = new Avatars();
        avatar.setUrl(filePath);
        avatar.setFilename(fileName);

        ///Видалити наявний аватар із сховища
        deleteAvatarByUserId(userId);

        avatar.setUser(user);
        userAvatarRepository.save(avatar);
    }

    ///Отримати список аватарок за списком користувацький Id
    public List<Avatars> getAvatarsByUserIds(List<Long> usersIds) {
        return userAvatarRepository.findByUserIds(usersIds);
    }


}
