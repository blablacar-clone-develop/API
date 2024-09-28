package org.booking.spring.repositories;

import org.booking.spring.models.trips.TripAgreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripAgreementRepository extends JpaRepository<TripAgreement, Long> {
}
