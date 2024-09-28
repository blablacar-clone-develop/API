package org.booking.spring.models.trips;

import jakarta.persistence.*;
import lombok.Data;
import org.booking.spring.models.user.User;

@Entity
@Table(name = "passengers")
@Data
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Багато пасажирів належать одній поїздці
    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trips trip;

    // Багато пасажирів можуть бути одним користувачем
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
