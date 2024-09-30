package org.booking.spring.models.trips;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonBackReference
    private Trips trip;
    @Override
    public String toString() {
        return "TripAgreement{" +
                "agreementId='" + isAgreed + '\'' +
                '}';
    }

}
