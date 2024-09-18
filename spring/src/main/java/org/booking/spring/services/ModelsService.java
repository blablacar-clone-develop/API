package org.booking.spring.services;

import org.booking.spring.models.auto.Model;
import org.booking.spring.repositories.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModelsService {
    @Autowired
    private ModelRepository modelRepository;

    public List<String> getModelsByBrandId(Long brand_id) {
        return modelRepository.findNamesByBrandId(brand_id);
    }
    public Optional<Model> findByNameAndBrandId(String modelName, Long brandId) {
        return modelRepository.findByNameAndBrandId(modelName, brandId);
    }
}
