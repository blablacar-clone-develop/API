package org.booking.spring.models.trips;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trip_agreements")
@Data
@NoArgsConstructor
public class TripAgreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Поле, що вказує, чи погоджує користувач самостійно
    @Column(name = "is_agreed", nullable = false)
    private Boolean isAgreed;

    // Зв'язок з таблицею Trips (ID поїздки)
    @OneToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trips trip;

}
