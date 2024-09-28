package org.booking.spring.repositories;

import org.booking.spring.models.trips.Trips;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripsRepository extends JpaRepository<Trips, Long> {
}
