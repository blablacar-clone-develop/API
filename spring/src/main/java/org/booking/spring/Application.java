package org.booking.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SpringBootApplication(scanBasePackages = "org.booking.spring")
@EnableJpaRepositories(basePackages = "org.booking.spring.repositories")
public class Application {

    @GetMapping("/hi")
    public String home() {
        return "Hello World";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
