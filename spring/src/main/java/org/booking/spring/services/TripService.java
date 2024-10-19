package org.booking.spring.services;

import org.booking.spring.models.trips.Trips;
import org.booking.spring.repositories.TripsRepository;
import org.booking.spring.responses.DTO.TripDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TripService {
    @Autowired
    private TripsRepository tripRepository;
    public Trips save(Trips trip) {
        return tripRepository.save(trip);
    }

    public Optional<Trips> findById(Long id) {
        return tripRepository.findById(id);
    }

    public TripDto findByIdDTO(long id) {
        return new TripDto(tripRepository.findById(id));

    }
    public List<Trips> getTripsByUserId(Long userId) {
        // Fetch trips by userId
        return tripRepository.findByUserId(userId);
    }

    public List<Trips> searchTrips(LocalDate departureDate, int passengerCount, String startCity, String startState, String finishCity, String finishState) {
        return tripRepository.findTripsByCriteria(departureDate, passengerCount, startCity, startState, finishCity, finishState);
    }
}
