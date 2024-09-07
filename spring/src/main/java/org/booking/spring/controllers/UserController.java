package org.booking.spring.controllers;

import org.booking.spring.models.user.User;
import org.booking.spring.requests.auth.SignInRequest;
import org.booking.spring.requests.auth.SignUpRequest;
import org.booking.spring.responses.UserLoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.booking.spring.services.*;
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final JwtUserService jwtUserService;

    public UserController(UserService userService, JwtUserService jwtUserService) {
        this.userService = userService;
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
            return ResponseEntity.ok().body(ex.getMessage());
        }
    }
    @PostMapping("/signIn")
    public ResponseEntity<?> userSignIn(@RequestBody SignInRequest signInRequest) {
        try {
            UserLoginResponse userLoginResponse = jwtUserService.signIn(signInRequest);
            return ResponseEntity.ok(userLoginResponse);
        } catch(Exception ex)
        {
            return ResponseEntity.ok().body("Error");
        }
    }
}
