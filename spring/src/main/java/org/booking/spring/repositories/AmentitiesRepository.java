package org.booking.spring.repositories;

import org.booking.spring.models.trips.Amentities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmentitiesRepository extends JpaRepository<Amentities, Long> {
}
