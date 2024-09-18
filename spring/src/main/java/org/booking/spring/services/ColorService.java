package org.booking.spring.services;

import org.booking.spring.models.auto.Color;
import org.booking.spring.repositories.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColorService {
    @Autowired
    private ColorRepository colorRepository;

    public List<Color> getAllColors() {
        return colorRepository.findAllColors();
    }

    public Optional<Color> findById(Long id) {
        return colorRepository.findById(id);
    }
}
