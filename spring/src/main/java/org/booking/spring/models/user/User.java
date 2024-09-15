package org.booking.spring.models.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.booking.spring.models.auto.Autos;
import org.booking.spring.models.trips.Trips;

import java.time.LocalDate;
import java.util.List;

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

    // Один користувач може мати декілька автомобілів
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Autos> autos;

    // Один користувач може мати декілька автомобілів
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Autos> autos;

    // Один користувач може мати багато подорожей
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
//    private List<Trips> trips;


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