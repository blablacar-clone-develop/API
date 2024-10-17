package org.booking.spring.responses.DTO;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.booking.spring.models.user.User;

import java.time.LocalDate;

@Setter
@Getter
public class UserDTO {

    private String name;
    private String surname;
    private LocalDate dateOfBirthday;
    private String gender;
    private String email;

    public UserDTO(User user) {
        this.name = user.getName();
        this.surname = user.getSurname();
        this.dateOfBirthday = user.getDateOfBirthday();
        this.gender = user.getGender();
        this.email = user.getEmail();
    }

}
