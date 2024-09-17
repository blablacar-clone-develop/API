package org.booking.spring.repositories;

import org.booking.spring.models.auto.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    Optional<Brand> findByName(String name);
    @Query("SELECT b.name FROM Brand b")
    List<String> findAllBrandNames();
    @Query(value = "SELECT b.name FROM cars_brands b JOIN autos a ON b.id = a.brand_id GROUP BY b.name ORDER BY COUNT(b.name) DESC LIMIT 5", nativeQuery = true)
    List<String> findTop5MostFrequentBrands();

    @Query(value = "SELECT b.id FROM cars_brands b WHERE b.name = :brand", nativeQuery = true)
    Long getBrandIdByName(@Param("brand") String brand);
}
