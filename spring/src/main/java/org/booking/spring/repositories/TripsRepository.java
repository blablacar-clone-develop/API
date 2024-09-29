package org.booking.spring.repositories;

import org.booking.spring.models.trips.Trips;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TripsRepository extends JpaRepository<Trips, Long> {
    @Query("SELECT t FROM Trips t " +
            "WHERE t.departureDate = :departureDate " +
            "AND t.availableSeats >= :passengerCount " +
            "AND t.startTravelPoint.city = :startCity " +
            "AND t.startTravelPoint.state = :startState " +
            "AND t.finishTravelPoint.city = :finishCity " +
            "AND t.finishTravelPoint.state = :finishState")
    List<Trips> findTripsByCriteria(LocalDate departureDate, int passengerCount, String startCity, String startState, String finishCity, String finishState);
}
