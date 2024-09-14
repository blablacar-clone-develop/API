package org.booking.spring.services;


import org.booking.spring.models.auto.Autos;
import org.booking.spring.repositories.AutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutosService {

    private final AutosRepository repository;

    @Autowired
    public AutosService(AutosRepository repository) {
        this.repository = repository;
    }

    public List<Autos> getAllAutos() {
        return repository.findAll();
    }
}
