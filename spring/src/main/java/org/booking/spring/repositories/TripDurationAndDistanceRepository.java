package org.booking.spring.repositories;

import org.booking.spring.models.trips.TripDurationAndDistance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripDurationAndDistanceRepository extends JpaRepository<TripDurationAndDistance, Long> {
}
