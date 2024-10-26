package org.booking.spring.repositories;

import org.booking.spring.models.trips.Passenger;
import org.booking.spring.models.trips.Trips;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    @Query("SELECT p.trip FROM Passenger p WHERE p.user.id = :userId")
    List<Trips> findTripsByUserId(@Param("userId") Long userId);

    List<Passenger> findByTripId(Long tripId);
}
