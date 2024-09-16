package org.booking.spring.requests.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedUserRequest {
    private String name;
    private String surname;
    private LocalDate dateOfBirthday;
    private String email;
    private String phoneNumber;
    private String description;
}

