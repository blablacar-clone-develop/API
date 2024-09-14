package org.booking.spring.services;


import jakarta.transaction.Transactional;
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


     //Перевіряє, чи є користувач власником конкретного автомобіля
    public boolean isUserOwnerOfAuto(Long autoId, Long userId) {
        // Знаходимо автомобіль за його ID
        Autos auto = repository.findById(autoId)
                .orElseThrow(() -> new IllegalArgumentException("Автомобіль з вказаним ID не знайдено"));
        // Порівнюємо userId у автомобіля з userId, переданим у параметрах
        return auto.getUser().getId().equals(userId);
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

    @Transactional
    public AutoDto updateAuto(Long autoId, Autos updatedAuto) {
        // Знайти авто за ID
        Autos existingAuto = repository.findById(autoId)
                .orElseThrow(() -> new IllegalArgumentException("Автомобіль з ID " + autoId + " не знайдено"));

        // Оновити поля
        existingAuto.setBrand(updatedAuto.getBrand());
        existingAuto.setModel(updatedAuto.getModel());
        existingAuto.setColor(updatedAuto.getColor());

        // Зберегти оновлений автомобіль
        repository.save(existingAuto);

        return new AutoDto(existingAuto.getId(), existingAuto.getBrand(), existingAuto.getModel(), existingAuto.getColor());
    }

    @Transactional
    public void deleteAuto(Long autoId) {
        if (!repository.existsById(autoId)) {
            throw new IllegalArgumentException("Автомобіль з ID " + autoId + " не знайдено");
        }
        repository.deleteById(autoId);
    }

    @Transactional
    public void deleteAllAutosByUserId(Long userId) {
        // Видаляємо всі авто, що належать користувачу з переданим userId
        repository.deleteByUserId(userId);
    }

}
