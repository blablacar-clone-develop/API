package org.booking.spring.models.auto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.booking.spring.models.trips.Trips;
import org.booking.spring.models.user.User;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "autos")
@Data
public class Autos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
