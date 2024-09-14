package org.booking.spring.repositories;


import org.booking.spring.models.auto.Autos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AutosRepository extends JpaRepository<Autos,Long> {

    Optional<Autos> findById(Long id);

    Optional<Autos> findByUserId(Long userId);
}
