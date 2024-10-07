package org.booking.spring.models.trips;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "amenities")
@Data
@Getter
@Setter
public class Amentities{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wifi")
    private boolean wifi;

    @Column(name = "e_tickets")
    private boolean eTickets;

    @Column(name = "air_conditioning")
    private boolean airConditioning;

    @Column(name = "smoking")
    private boolean smoking;

    @Column(name = "pets_allowed")
    private boolean petsAllowed;

    @Column(name = "food_provided")
    private boolean foodProvided;

    @OneToOne
    @JoinColumn(name = "trip_id")
    @JsonBackReference
    private Trips trip;
    @Override
    public String toString() {
        return "Amentities{" +
                "optionId=" + id +
                '}';
    }
}