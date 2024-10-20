package org.booking.spring.services;

import org.booking.spring.models.trips.Passenger;
import org.booking.spring.repositories.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassengerService {
    @Autowired
    private PassengerRepository passengerRepository;
    public Passenger save(Passenger p) {
        return passengerRepository.save(p);
    }
}
