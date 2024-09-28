package org.booking.spring.models.trips;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "options_trip")
@Data
public class Options {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "max_two_passengers", nullable = false)
    private Boolean maxTwoPassengers;

    @Column(name = "women_only", nullable = false)
    private Boolean womenOnly;

    // Зв'язок з поїздкою (один до одного)
    @OneToOne
    @JoinColumn(name = "trip_id")
    private Trips trip;
}
