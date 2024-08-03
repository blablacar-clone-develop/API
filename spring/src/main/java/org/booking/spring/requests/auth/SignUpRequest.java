package org.booking.spring.requests.auth;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Schema(description = "Запрос на регистрацию")
public class SignUpRequest {

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Schema(description = "Пароль", example = "Password123")
    private String password;

    @NotBlank(message = "Name is mandatory")
    @Schema(description = "Имя", example = "John")
    private String name;

    @NotBlank(message = "Surname is mandatory")
    @Schema(description = "Фамилия", example = "Doe")
    private String surname;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Schema(description = "Email", example = "john.doe@example.com")
    private String email;

    @NotNull(message = "Date of birth is mandatory")
    @Past(message = "Date of birth must be in the past")
    @Schema(description = "Дата рождения", example = "1990-01-01")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Gender is mandatory")
    @Schema(description = "Пол", example = "Male")
    private String gender;
}