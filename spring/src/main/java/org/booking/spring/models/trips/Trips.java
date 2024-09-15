package org.booking.spring.models.trips;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.booking.spring.models.auto.Autos;
import org.booking.spring.models.user.User;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "trips")
@Data
public class Trips {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Зв'язок "багато до багатьох" з Autos
    @ManyToMany(mappedBy = "trips")
    private List<Autos> autos;

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
