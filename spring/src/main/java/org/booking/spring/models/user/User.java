package org.booking.spring.models.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseUserEntity {

    private String name;
    private String surname;

    @Column(name = "date_of_birthday")
    private LocalDate dateOfBirthday;

    private String gender;

    @ManyToOne
    @JoinColumn(name = "id_permission", insertable = false, updatable = false)
    private UserPermissions userPermissions;

    @Override
    protected void onUpdate() {
        super.onUpdate();
        validateName();
    }

    private void validateName() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
    }
}