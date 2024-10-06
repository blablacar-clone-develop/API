package org.booking.spring.controllers;

import org.booking.spring.config.email.EmailSender;
import org.booking.spring.models.user.*;
import org.booking.spring.repositories.UserVerificationRepository;
import org.booking.spring.requests.auth.SignInRequest;
import org.booking.spring.requests.auth.SignUpRequest;
import org.booking.spring.requests.auth.UpdatedUserRequest;
import org.booking.spring.responses.ResponseUserVerification;
import org.booking.spring.responses.UserLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.booking.spring.services.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final UsersContactService usersContactService;
    @Autowired
    private  UserVerificationRepository userVerificationRepository;
    private final AccountInfoService accountInfoService;
    private final JwtUserService jwtUserService;
    private final EmailVerificationService emailVerificationService;
    private final UserVerificationService userVerificationService;
    private final VerificationCodeService verificationCodeService;

    public UserController(UserService userService, UsersContactService usersContactService, AccountInfoService accountInfoService, JwtUserService jwtUserService, EmailVerificationService emailVerificationService, UserVerificationService userVerificationService, VerificationCodeService verificationCodeService) {
        this.userService = userService;
        this.usersContactService = usersContactService;
        this.accountInfoService = accountInfoService;
        this.jwtUserService = jwtUserService;
        this.emailVerificationService = emailVerificationService;
        this.userVerificationService = userVerificationService;
        this.verificationCodeService = verificationCodeService;
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserData(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);

        try {
            User user = jwtUserService.getUserData(token);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.ok("token");
            }
        }catch(Exception ex)
        {
            return ResponseEntity.ok("token");
        }


    }

    @GetMapping("/user/verification/{id}")
    public ResponseEntity<?> getUserVerification(@PathVariable("id") Long id) {
        try {
            UserVerification userVerification = userVerificationRepository.findByUserId(id);

            if (userVerification == null) {
                ResponseUserVerification response = new ResponseUserVerification(false, false, false);
                return ResponseEntity.ok(response);
            }

            ResponseUserVerification response = new ResponseUserVerification(
                    userVerification.getEmailVerified(),
                    userVerification.getPhoneVerified(),
                    userVerification.getDocumentVerified()
            );

            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/verifyCode")
    public ResponseEntity<String> verifyCode(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody Map<String, String> requestBody) {

        String jwtToken = authorizationHeader.substring(7);
        Long userId = jwtUserService.extractUserId(jwtToken);
        String code = requestBody.get("completeCode");
        EmailVerification codeDB = emailVerificationService.verifyEmailCode(userId);
        System.out.println("КОД которий пріслал пользователь"+ code);

        if(!codeDB.isExpired())
        {
            if(code.equals(codeDB.getVerificationCode()))
            {
                System.out.println("КОДи совпалі");
                emailVerificationService.delete(codeDB);
                userVerificationService.verifyEmailUserById(userId);
                return ResponseEntity.ok("code");
            }
            else {
                System.out.println("КОД не коректний");
                return ResponseEntity.ok("incorrect");
            }
        } else {
            System.out.println("Вишло время");
            return ResponseEntity.ok("time");
        }

    }


    /////Підходить для підтвердження електронної пошти
    @GetMapping("/user/getEmailCode")
    public String getUserEmailCode(@RequestHeader("Authorization") String authorizationHeader) {
        try {

            // Видаляємо "Bearer " із заголовка
            String jwtToken = authorizationHeader.substring(7);
            Long userId = jwtUserService.extractUserId(jwtToken);
            Optional<User> existingUser = userService.getUserById(userId);

            // Перевіряємо, чи вже є активний код для цього користувача
            EmailVerification existingVerification = emailVerificationService.verifyEmailCode(userId);

            if (existingVerification != null) {
                    // термін дії коду сплив алгоритм нижче
                    if(existingVerification.isExpired()) {
                        System.out.println("Термін дії коду сплив -  "+ existingVerification.isExpired());
                        String code = verificationCodeService.generateVerificationCode();
                        existingVerification.setVerificationCode(code);
                        existingVerification.setCreatedAt(LocalDateTime.now());
                        existingVerification.setExpiresAt(LocalDateTime.now().plusMinutes(5));
                        emailVerificationService.save(existingVerification);
                        EmailSender.sendEmail(
                                existingUser.get().getEmail(),
                                "Verification code",
                                "verificationCode :" + code
                        );
                        return "code";
                    }
                System.out.println("ТЕКУЩИЙ КОД "+ existingVerification.getVerificationCode());
                return "Код уже було відправлено на електронну пошту"; // Код вже існує, не потрібно відправляти емейл
            }

            // Генеруємо новий код
            String verificationCode = verificationCodeService.generateVerificationCode();
            EmailSender.sendEmail(
                    existingUser.get().getEmail(),
                    "Verification code",
                    "verificationCode :" + verificationCode
            );

            // Створюємо новий запис EmailVerification
            EmailVerification emailVerification = new EmailVerification();
            emailVerification.setVerificationCode(verificationCode);
            emailVerification.setExpiresAt(LocalDateTime.now().plusMinutes(5));
            emailVerification.setUserId(existingUser.get().getId());
            EmailVerification e = emailVerificationService.save(emailVerification);

            if (e != null) {
                return "code";
            } else {
                return "mistake";
            }
        } catch (Exception e) {
            System.out.println("Error in  public String getUserEmailCode \n" + e.getMessage());
            return null;
        }
    }



    @PostMapping("/signUp")
    public ResponseEntity<?> userSignUp(@RequestBody SignUpRequest signUpRequest) {

        try {
            User user = userService.registerNewUserAccount(signUpRequest);
            UserLoginResponse userLoginResponse = jwtUserService.signUp(user);

            userVerificationService.addInfoAboutNewUserById(user.getId());

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
