package org.booking.spring.repositories;

import org.booking.spring.models.auto.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
    Optional<Model> findByName(String name);

    @Query(value = "SELECT c.name FROM cars_models c WHERE c.brand_id = :brandId", nativeQuery = true)
    List<String> findNamesByBrandId(@Param("brandId") Long brandId);
}
