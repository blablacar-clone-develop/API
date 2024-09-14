package org.booking.spring.services;


import org.booking.spring.models.auto.Autos;
import org.booking.spring.repositories.AutosRepository;
import org.booking.spring.responses.DTO.AutoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutosService {

    private final AutosRepository repository;

    @Autowired
    public AutosService(AutosRepository repository) {
        this.repository = repository;
    }

    public List<AutoDto> getAllAutos() {
        List<Autos> autosList = repository.findAll();
        return autosList.stream()
                .map(auto -> new AutoDto(auto.getId(), auto.getBrand(), auto.getModel(), auto.getColor()))
                .collect(Collectors.toList());
    }

    public List<AutoDto> getAutosByUserId(Long userId) {
        List<Autos> autosList = repository.findByUserId(userId);
        // Мапінг сутності Autos у DTO
        return autosList.stream()
                .map(auto -> new AutoDto(auto.getId(), auto.getBrand(), auto.getModel(), auto.getColor()))
                .collect(Collectors.toList());

    }


}
