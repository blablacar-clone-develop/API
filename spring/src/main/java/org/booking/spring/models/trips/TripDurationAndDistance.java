package org.booking.spring.models.trips;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trip_duration_and_distance")
@Data
@NoArgsConstructor
public class TripDurationAndDistance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Час на дорогу (у хвилинах або годинах, як вирішите)
    @Column(name = "duration", nullable = false)
    private String duration;

    // Відстань у кілометрах
    @Column(name = "distance", nullable = false)
    private String distance;

    // Зв'язок з таблицею Trips (ID поїздки)
    @OneToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trips trip;


}