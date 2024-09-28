package org.booking.spring.controllers;

import org.booking.spring.models.trips.*;

import org.booking.spring.repositories.OptionsRepository;
import org.booking.spring.repositories.TripAgreementRepository;
import org.booking.spring.repositories.TripDurationAndDistanceRepository;
import org.booking.spring.responses.DTO.AutoDto;
import org.booking.spring.responses.DTO.TripDto;
import org.booking.spring.services.JwtUserService;
import org.booking.spring.services.TravelPointsService;
import org.booking.spring.services.TripService;
import org.booking.spring.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/trips")
public class TripController {
    private final TripService tripService;
    private final OptionsRepository optionsRepository;
    private final TripDurationAndDistanceRepository tripDurationAndDistanceRepository;
    private final TripAgreementRepository tripAgreementRepository;
    private final TravelPointsService travelPointsService;
    private final UserService userService;
    private final JwtUserService jwtUserService;
    public TripController(TripService tripService, OptionsRepository optionsRepository, TripDurationAndDistanceRepository tripDurationAndDistanceRepository, TripAgreementRepository tripAgreementRepository, TravelPointsService travelPointsService, UserService userService, JwtUserService jwtUserService) {
        this.tripService = tripService;
        this.optionsRepository = optionsRepository;
        this.tripDurationAndDistanceRepository = tripDurationAndDistanceRepository;
        this.tripAgreementRepository = tripAgreementRepository;
        this.travelPointsService = travelPointsService;
        this.userService = userService;
        this.jwtUserService = jwtUserService;
    }
    private Double convertToDouble(Object value) {
        if (value instanceof Integer) {
            return ((Integer) value).doubleValue();
        } else if (value instanceof String) {
            return Double.valueOf((String) value);
        } else {
            return (Double) value;
        }
    }

    private double parseDistance(String distanceStr) {
        // Видаляємо все, що не є цифрою або десятковою крапкою
        String cleanedDistance = distanceStr.replaceAll("[^\\d.]", "");
        return Double.parseDouble(cleanedDistance);
    }


    private double parseDuration(String durationStr) {
        // Видаляємо непотрібні символи і працюємо з частинами
        durationStr = durationStr.replace(" ч.", "").replace(" мин.", "");
        String[] parts = durationStr.split(" ");  // Розділяємо години та хвилини
        double hours = Double.parseDouble(parts[0]);  // Години
        double minutes = Double.parseDouble(parts[1]) / 60;  // Хвилини в частку години
        return hours + minutes;  // Повертаємо загальний час у годинах
    }
    @PostMapping("/create")
    public ResponseEntity<?> createTrip(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, Object> tripData)
    {
        try {
            String jwtToken = token.substring(7);  // Видаляємо "Bearer " із заголовка
            Long userId = jwtUserService.extractUserId(jwtToken);  // Витягуємо userId з токена
            Map<String, Object> fromAddress = (Map<String, Object>) tripData.get("fromAddress");
            Map<String, Object> toAddress = (Map<String, Object>) tripData.get("toAddress");

            TravelPoints travelPointFrom = new TravelPoints();
            travelPointFrom.setLatitude((fromAddress.get("latitude") instanceof String) ?
                    Double.valueOf((String) fromAddress.get("latitude")) : (Double) fromAddress.get("latitude"));
            travelPointFrom.setLongitude((fromAddress.get("longitude") instanceof String) ?
                    Double.valueOf((String) fromAddress.get("longitude")) : (Double) fromAddress.get("longitude"));
            travelPointFrom.setCity((String) fromAddress.get("city"));
            travelPointFrom.setState((String) fromAddress.get("country"));

            TravelPoints travelPointTo = new TravelPoints();
            travelPointTo.setLatitude((toAddress.get("latitude") instanceof String) ?
                    Double.valueOf((String) toAddress.get("latitude")) : (Double) toAddress.get("latitude"));
            travelPointTo.setLongitude((toAddress.get("longitude") instanceof String) ?
                    Double.valueOf((String) toAddress.get("longitude")) : (Double) toAddress.get("longitude"));
            travelPointTo.setCity((String) toAddress.get("city"));
            travelPointTo.setState((String) toAddress.get("country"));

            travelPointFrom = travelPointsService.saveTravelPoint(travelPointFrom);
            travelPointTo = travelPointsService.saveTravelPoint(travelPointTo);

            Trips trip = new Trips();
            trip.setStartTravelPoint(travelPointFrom);
            trip.setFinishTravelPoint(travelPointTo);
            trip.setPassengerCount((Integer) tripData.get("passengers"));
            trip.setPrice(convertToDouble(tripData.get("price")));
            trip.setAvailableSeats((Integer) tripData.get("passengers"));
            String selectedTime = (String) tripData.get("selectedTime");
            String selectedDate = (String) tripData.get("date");

            LocalTime time = LocalTime.parse(selectedTime, DateTimeFormatter.ofPattern("HH:mm"));
            OffsetDateTime date = OffsetDateTime.parse(selectedDate, DateTimeFormatter.ISO_OFFSET_DATE_TIME);

            trip.setDepartureTime(time);
            trip.setDepartureDate(date.toLocalDate());
            trip.setUser(userService.getUserById(userId).get());

            Map<String, Object> optionsData = (Map<String, Object>) tripData.get("options");

            Options options = new Options();
            options.setMaxTwoPassengers((Boolean) optionsData.get("maxTwoPassengers"));
            options.setWomenOnly((Boolean) optionsData.get("womenOnly"));
            options.setTrip(trip);

            Map<String, Object> selectedRoute = (Map<String, Object>) tripData.get("selectedRoute");
            TripDurationAndDistance  tripDurationAndDistance = new TripDurationAndDistance();
            String distanceStr = (String) selectedRoute.get("distance");
            double distance = parseDistance(distanceStr);

            String durationStr = (String) selectedRoute.get("duration");
            double duration = parseDuration(durationStr);
            tripDurationAndDistance.setDistance(distance);
            tripDurationAndDistance.setDuration(duration);

            TripAgreement tripAgreement = new TripAgreement();
            tripAgreement.setIsAgreed(tripData.get("selectBooking") != "each");

            Trips savedTrip = tripService.save(trip);
            tripDurationAndDistance.setTrip(savedTrip);
            tripDurationAndDistanceRepository.save(tripDurationAndDistance);
            tripAgreement.setTrip(savedTrip);
            tripAgreementRepository.save(tripAgreement);

            options.setTrip(savedTrip);
            optionsRepository.save(options);
            return ResponseEntity.ok(savedTrip.getId());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @GetMapping("/getTripById/{id}")
    public ResponseEntity<Trips> getTripsById(@PathVariable("id") Long id)
    {
        try {
            Optional<Trips> tripOptional = tripService.findById(id);
            if (tripOptional.isPresent()) {
                return ResponseEntity.ok(tripOptional.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
