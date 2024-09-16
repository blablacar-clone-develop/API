package org.booking.spring.controllers;

import org.booking.spring.models.user.AccountInfo;
import org.booking.spring.models.user.User;
import org.booking.spring.models.user.UsersContact;
import org.booking.spring.requests.auth.SignInRequest;
import org.booking.spring.requests.auth.SignUpRequest;
import org.booking.spring.requests.auth.UpdatedUserRequest;
import org.booking.spring.responses.UserLoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.booking.spring.services.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final UsersContactService usersContactService;
    private final AccountInfoService accountInfoService;
    private final JwtUserService jwtUserService;

    public UserController(UserService userService, UsersContactService usersContactService, AccountInfoService accountInfoService, JwtUserService jwtUserService) {
        this.userService = userService;
        this.usersContactService = usersContactService;
        this.accountInfoService = accountInfoService;
        this.jwtUserService = jwtUserService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> userSignUp(@RequestBody SignUpRequest signUpRequest) {

        try {
            User user = userService.registerNewUserAccount(signUpRequest);
            UserLoginResponse userLoginResponse = jwtUserService.signUp(user);
            return ResponseEntity.ok(userLoginResponse);
        } catch(Exception ex)
        {
            // Повертаємо статус 400 Bad Request при помилках реєстрації
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
    @PostMapping("/signIn")
    public ResponseEntity<?> userSignIn(@RequestBody SignInRequest signInRequest) {
        try {
            UserLoginResponse userLoginResponse = jwtUserService.signIn(signInRequest);
            return ResponseEntity.ok(userLoginResponse);
        } catch(Exception ex)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }
    @PutMapping("usersData/update/{userId}")
    public ResponseEntity<User> updateUserData(@PathVariable Long userId, @RequestBody UpdatedUserRequest updatedUserRequest) {
        Optional<User> existingUser = userService.getUserById(userId);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setName(updatedUserRequest.getName());
            user.setSurname(updatedUserRequest.getSurname());
            user.setDateOfBirthday(updatedUserRequest.getDateOfBirthday());
            user.setEmail(updatedUserRequest.getEmail());

            if(updatedUserRequest.getPhoneNumber() != null) {
                Optional<UsersContact> existingContact = usersContactService.getByUserId(userId);
                if (existingContact.isPresent()) {
                    UsersContact usersContact = existingContact.get();
                    usersContact.setPhoneNumber(updatedUserRequest.getPhoneNumber());
                    usersContactService.updateContact(usersContact);
                } else {
                    UsersContact newContact = new UsersContact();
                    newContact.setUserId(userId);
                    newContact.setPhoneNumber(updatedUserRequest.getPhoneNumber());
                    usersContactService.saveContact(newContact);
                }
            }

            if(updatedUserRequest.getDescription() != null) {
                Optional<AccountInfo> existingAccountInfo = accountInfoService.getByUserId(userId);
                if (existingAccountInfo.isPresent()) {
                    AccountInfo accountInfo = existingAccountInfo.get();
                    accountInfo.setDescription(updatedUserRequest.getDescription());
                    accountInfoService.updateAccountInfo(accountInfo);
                } else {
                    AccountInfo newAccountInfo = new AccountInfo();
                    newAccountInfo.setUserId(userId);
                    newAccountInfo.setDescription(updatedUserRequest.getDescription());
                    accountInfoService.saveAccountInfo(newAccountInfo);
                }
            }


            User savedUser = userService.updateUser(user);
            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/usersData/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
