package org.booking.spring.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.booking.spring.models.auto.Autos;
import org.booking.spring.models.trips.Passenger;
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Autos> autos;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Passenger> passengers;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Trips> trips;

    //Зв'язок з аватаркою
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Avatars avatar;

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
    @Override
    public String toString() {
        return "User{id=" + getId() + ", name='" + name + "', surname='" + surname + "', dateOfBirthday=" + dateOfBirthday + ", gender='" + gender + "'}";
    }
}
