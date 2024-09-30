package org.booking.spring.models.trips;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    private Trips trip;
    @Override
    public String toString() {
        return "Options{" +
                "optionId=" + id +
                // Add fields that do not recursively call `Trips.toString()`
                '}';
    }
}
