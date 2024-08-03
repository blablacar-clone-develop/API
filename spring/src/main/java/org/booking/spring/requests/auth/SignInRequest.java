package org.booking.spring.requests.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Schema(description = "Запрос на регистрацию")
public class SignInRequest {

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Schema(description = "Пароль", example = "Password123")
    private String password;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Schema(description = "Email", example = "john.doe@example.com")
    private String email;


}
