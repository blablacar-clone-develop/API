package org.booking.spring.responses.DTO;

import lombok.Getter;
import lombok.Setter;
import org.booking.spring.models.trips.Trips;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Setter
@Getter
public class TripDto {
    private Long id;
    private int passenger_count;
    private int available_seats;
    private double price;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private AutoDto auto;
    private TravelPointDto  start_travel_point;
    private TravelPointDto  finish_travel_point;
    private AmentitiesDTO amentities;
    private UserDTO user;
    private TripDurationAndDistanceDTO trip_duration;
    private Boolean isAgreed;

    public TripDto(Optional<Trips> trip) {
        this.id = trip.get().getId();
        this.passenger_count = trip.get().getPassengerCount();
        this.available_seats = trip.get().getAvailableSeats();
        this.price = trip.get().getPrice();
        this.departureDate = trip.get().getDepartureDate();
        this.departureTime = trip.get().getDepartureTime();
        this.start_travel_point = new TravelPointDto(trip.get().getStartTravelPoint());
        this.finish_travel_point = new TravelPointDto(trip.get().getFinishTravelPoint());
        this.user = new UserDTO(trip.get().getUser());
        this.amentities = new AmentitiesDTO(trip.get().getAmenities());
        this.trip_duration = new TripDurationAndDistanceDTO(trip.get().getTripDurationAndDistance());
        this.isAgreed = trip.get().getTripAgreement().getIsAgreed();
        this.auto = new AutoDto(trip.get().getAutos());
    }
}

