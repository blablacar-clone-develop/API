package org.booking.spring.services;

import org.booking.spring.models.trips.Passenger;
import org.booking.spring.repositories.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PassengerService {
    @Autowired
    private PassengerRepository passengerRepository;
    public Passenger save(Passenger p) {
        return passengerRepository.save(p);
    }

    public List<Long> getPassengerIdsByTripId(Long tripId) {
        return passengerRepository.findByTripId(tripId).stream()
                .map(Passenger::getId)
                .collect(Collectors.toList());
    }

    public List<Passenger> getPassengerByTripId(Long id) {
        return passengerRepository.findByTripId(id);
    }
}
