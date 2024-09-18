package org.booking.spring.repositories;

import org.booking.spring.models.auto.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
    @Query(value = "SELECT c.id, c.name, c.hex FROM colors c", nativeQuery = true)
    List<Color> findAllColors();
}
