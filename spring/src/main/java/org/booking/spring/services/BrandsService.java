package org.booking.spring.services;

import org.booking.spring.models.auto.Brand;
import org.booking.spring.repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandsService {
    @Autowired
    private BrandRepository brandRepository;
    public List<String> getAllAutosBrand() {
        return brandRepository.findAllBrandNames();
    }

    public List<String> getTop5Brands() {
        return brandRepository.findTop5MostFrequentBrands();
    }

    public Long getBrandIdByName(String brand) {
        return  brandRepository.getBrandIdByName(brand);
    }

    public Optional<Brand> findByName(String brandName) {
        return brandRepository.findByName(brandName);
    }
}
