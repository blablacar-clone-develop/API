package org.booking.spring.models.auto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.booking.spring.models.baseEntity.BaseEntity;
import org.booking.spring.models.trips.Trips;
import org.booking.spring.models.user.BaseUserEntity;
import org.booking.spring.models.user.User;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "autos")
@Data
public class Autos extends BaseEntity {

    // Багато автомобілів належать одному користувачу
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    // Зв'язок "багато до багатьох" з Trips
    @ManyToMany
    @JoinTable(
            name = "autos_trips",
            joinColumns = @JoinColumn(name = "auto_id"),
            inverseJoinColumns = @JoinColumn(name = "trip_id")
    )
    private List<Trips> trips;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "color", nullable = false)
    private String color;

}
