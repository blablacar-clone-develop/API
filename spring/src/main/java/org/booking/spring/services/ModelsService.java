package org.booking.spring.services;

import org.booking.spring.repositories.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelsService {
    @Autowired
    private ModelRepository modelRepository;

    public List<String> getModelsByBrandId(Long brand_id) {
        return modelRepository.findNamesByBrandId(brand_id);
    }
}
