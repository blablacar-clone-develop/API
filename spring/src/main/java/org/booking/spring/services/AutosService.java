package org.booking.spring.services;


import org.booking.spring.models.auto.Autos;
import org.booking.spring.models.user.User;
import org.booking.spring.repositories.AutosRepository;
import org.booking.spring.repositories.UserRepository;
import org.booking.spring.responses.DTO.AutoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutosService {

    private final AutosRepository repository;

    @Autowired
    private UserRepository userRepository;

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

    public AutoDto getAutoById(Long id) {
        Autos auto = repository.findById(id).orElse(null);
        return new AutoDto(auto.getId(), auto.getBrand(), auto.getModel(), auto.getColor());
    }

    public AutoDto returnAutoDto(Autos auto) {
        return new AutoDto(auto.getId(), auto.getBrand(), auto.getModel(), auto.getColor());
    }

    public List<AutoDto> getAutosByUserId(Long userId) {
        List<Autos> autosList = repository.findByUserId(userId);
        // Мапінг сутності Autos у DTO
        return autosList.stream()
                .map(auto -> new AutoDto(auto.getId(), auto.getBrand(), auto.getModel(), auto.getColor()))
                .collect(Collectors.toList());

    }


    public Autos createAutoForUser(Long userId, Autos auto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        auto.setUser(user); // Прив'язуємо автомобіль до користувача
        return repository.save(auto); // Зберігаємо автомобіль у базі
    }
}
