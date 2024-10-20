package org.booking.spring.models.trips;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    @JsonBackReference
    private Trips trip;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    @JsonBackReference
    private User user;

    // New field for passenger type
    @Column(name = "passenger_type")
    private String passengerType;

    @Override
    public String toString() {
        return "Passenger{" +
                "id=" + id +
                ", user=" + (user != null ? user.getName() : passengerType) + // Display name if user exists, else passengerType
                '}';
    }
}