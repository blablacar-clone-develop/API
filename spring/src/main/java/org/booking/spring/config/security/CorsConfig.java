package org.booking.spring.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true); // Дозволити передачу credentials
        corsConfiguration.addAllowedOriginPattern("*"); // Дозволити всі домени; для безпечнішого підходу вкажіть конкретні домени
        corsConfiguration.addAllowedHeader("*"); // Дозволити всі заголовки
        corsConfiguration.addAllowedMethod("*"); // Дозволити всі HTTP методи (GET, POST, PUT, DELETE, тощо)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration); // Дозволити CORS для всіх шляхів

        return new CorsFilter(source);
    }
}
