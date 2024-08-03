package org.booking.spring.services;

import io.jsonwebtoken.Jwts;
import org.booking.spring.models.user.User;
import org.booking.spring.repositories.UserRepository;
import org.booking.spring.requests.auth.SignInRequest;
import org.booking.spring.responses.UserLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


@Service
public class JwtUserService {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;

    public UserLoginResponse signUp(User user) {
        String accessToken = generateAccessToken(user.getId(), user.getEmail());
        return new UserLoginResponse(user.getId(), accessToken);
    }
    @Value("${jwt.secret}")
    private String secretKey;
    private String generateAccessToken(Long userId, String email) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(key)
                .compact();
    }

    public UserLoginResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("User с указанным email не найден"));
        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Password");
        }
        String accessToken = generateAccessToken(user.getId(), user.getEmail());
        return new UserLoginResponse(user.getId(), accessToken);
    }
}
