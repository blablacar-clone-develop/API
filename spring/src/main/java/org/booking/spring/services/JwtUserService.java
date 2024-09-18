package org.booking.spring.services;

import io.jsonwebtoken.*;
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
import java.util.Optional;

import static io.jsonwebtoken.Jwts.*;


@Service
public class JwtUserService {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Value("${jwt.secret}")
    private String secretKey;

    public UserLoginResponse signUp(User user) {
        String accessToken = generateAccessToken(user.getId(), user.getEmail());
        return new UserLoginResponse(user.getId(), user.getName(),accessToken);
    }

    private String generateAccessToken(Long userId, String email) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        return builder()
                .setSubject(userId.toString())
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(key)
                .compact();
    }
    public boolean isTokenExpired(Claims claims) {
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }
    public User getUserData(String token) throws Exception {
        boolean flag = true;
        Claims claims = parseToken(token);
        if (claims != null) {
            if (!isTokenExpired(claims)) {
                String userIdString = claims.getSubject();
                Long userId = Long.parseLong(userIdString);
                Optional<User> userOptional = userRepository.findById(userId);
                if (userOptional.isPresent()) {
                    return userOptional.get();
                }

            }
            else flag=false;

        }
        else flag=false;
        if(!flag)
            throw new Exception("token");
        return null;

    }
    public Claims parseToken(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
            return Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }
    public UserLoginResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("User с указанным email не найден"));
        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Password");
        }
        String accessToken = generateAccessToken(user.getId(), user.getEmail());
        return new UserLoginResponse(user.getId(), user.getName() ,accessToken);
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public Long extractUserId(String token) {
        JwtParserBuilder parserBuilder = Jwts.parser().setSigningKey(getSigningKey());
        JwtParser parser = parserBuilder.build();
        Claims claims = parser.parseClaimsJws(token).getPayload();
        return Long.parseLong(claims.getSubject());
    }
}
