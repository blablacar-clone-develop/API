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

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToMany
    @JoinTable(
            name = "autos_trips",
            joinColumns = @JoinColumn(name = "auto_id"),
            inverseJoinColumns = @JoinColumn(name = "trip_id")
    )
    private List<Trips> trips;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private Model model;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;
    @Override
    public String toString() {
        return "Autos{id=" + getId() + ", brand=" + brand + ", model=" + model + ", color=" + color + "}";
    }
}
