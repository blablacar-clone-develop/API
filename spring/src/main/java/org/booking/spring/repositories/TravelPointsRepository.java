package org.booking.spring.repositories;

import org.booking.spring.models.trips.TravelPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelPointsRepository extends JpaRepository<TravelPoints, Long> {
}