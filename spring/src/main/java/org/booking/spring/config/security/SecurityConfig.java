package org.booking.spring.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authz -> authz
                        .requestMatchers(
                                "/api/signUp",
                                "api/signIn",
                                "hi",
                                "api/files/upload/avatar",
                                "api/files/avatar",
                                "api/autos/getAllAutos",
                                "api/autos/create",
                                "api/autos/user/*",
                                "api/autos/update/*",
                                "api/autos/delete/*",
                                "api/autos/getByAutoId/*",
                                "api/usersData/*",
                                "api/usersData/update/*",
                                "api/autos/brands/all",
                                "api/autos/brands/top",
                                "api/autos/models/all/*",
                                "api/autos/colors/all",
                                "api/autos/create",
                                "api/user",
                                "api/user/verification/*",
                                "api/user/getEmailCode",
                                "api/trips/create",
                                "api/trips/getSearchTrip",
                                "api/trips/getTripById/*",
                                "api/verifyCode",
                                "api/sendCode",
                                "api/phone/verifyCode",
                                "api/trips/users",
                                "api/passengers/save",
                                "api/passengers/*",
                                "api/trips/getPassengers/*",
                                "api/users/hasCar/*"

                                        ).permitAll()
                                        .anyRequest().authenticated()
                );
        //.httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
