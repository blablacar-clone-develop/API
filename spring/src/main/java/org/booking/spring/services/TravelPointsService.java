package org.booking.spring.services;

import org.booking.spring.models.trips.TravelPoints;
import org.booking.spring.repositories.TravelPointsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TravelPointsService {

    @Autowired
    private TravelPointsRepository travelPointsRepository;

    public TravelPoints saveTravelPoint(TravelPoints travelPoint) {
        return travelPointsRepository.save(travelPoint);
    }
}