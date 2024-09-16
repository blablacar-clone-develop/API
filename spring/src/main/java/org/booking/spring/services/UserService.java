package org.booking.spring.services;

import org.booking.spring.models.user.User;
import org.booking.spring.models.user.UserPermissions;
import org.booking.spring.repositories.UserRepository;
import org.booking.spring.requests.auth.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    public User registerNewUserAccount(SignUpRequest signUpRequest) {
        if (signUpRequest.getName() == null || signUpRequest.getSurname() == null ||
                signUpRequest.getEmail() == null || signUpRequest.getPassword() == null ||
                signUpRequest.getGender() == null || signUpRequest.getDateOfBirth() == null) {
            throw new IllegalArgumentException("All fields are required for registration");
        }

        if (emailExists(signUpRequest.getEmail())) {
            throw new IllegalArgumentException("This email has already exist");
        }

        User user = new User();
        user.setName(signUpRequest.getName());
        user.setSurname(signUpRequest.getSurname());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setDateOfBirthday(signUpRequest.getDateOfBirth());
        user.setGender(signUpRequest.getGender());

        //User Permission
        UserPermissions userPermissions = new UserPermissions();
        userPermissions.setName("User");
        userPermissions.setId(1L);
        user.setUserPermissions(userPermissions);

        return userRepository.save(user);
    }

    private boolean emailExists(String email) {
        return  userRepository.findByEmail(email).isPresent();
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }
}
