package org.booking.spring.models.trips;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.booking.spring.models.auto.Autos;
import org.booking.spring.models.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    // Багато автомобілів належать одному користувачу
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Passenger> passengers;

    @Column(name = "passenger_count", nullable = false)
    private Integer passengerCount;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "available_seats", nullable = false)
    private Integer availableSeats;

    // Поле для дати виїзду
    @Column(name = "departure_date", nullable = false)
    private LocalDate departureDate;

    // Поле для часу виїзду
    @Column(name = "departure_time", nullable = false)
    private LocalTime departureTime;

    // Зв'язок з TravelPoint для початкової точки
    @ManyToOne
    @JoinColumn(name = "start_travel_point_id")
    private TravelPoints startTravelPoint;
    @OneToOne(mappedBy = "trip", cascade = CascadeType.ALL)
    private Options options;
    // Зв'язок з TravelPoint для кінцевої точки
    @ManyToOne
    @JoinColumn(name = "finish_travel_point_id")
    private TravelPoints finishTravelPoint;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "trip", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private TripAgreement tripAgreement;


    // Зв'язок з TripDurationAndDistance
    @OneToOne(mappedBy = "trip", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private TripDurationAndDistance tripDurationAndDistance;

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
