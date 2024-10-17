package org.booking.spring.controllers;

import org.booking.spring.models.auto.Autos;
import org.booking.spring.models.trips.*;
import java.util.ArrayList;
import java.util.List;

import org.booking.spring.repositories.AmentitiesRepository;
import org.booking.spring.repositories.OptionsRepository;
import org.booking.spring.repositories.TripAgreementRepository;
import org.booking.spring.repositories.TripDurationAndDistanceRepository;
import org.booking.spring.requests.auth.SearchTripRequest;
import org.booking.spring.responses.DTO.TripDto;
import org.booking.spring.services.*;
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
    private final AmentitiesRepository amentitiesRepository;
    private final TravelPointsService travelPointsService;
    private final UserService userService;
    private final JwtUserService jwtUserService;
    private final AutosService autosService;

    public TripController(TripService tripService, OptionsRepository optionsRepository, TripDurationAndDistanceRepository tripDurationAndDistanceRepository, TripAgreementRepository tripAgreementRepository, AmentitiesRepository amentitiesRepository, TravelPointsService travelPointsService, UserService userService, JwtUserService jwtUserService, AutosService autosService) {
        this.tripService = tripService;
        this.optionsRepository = optionsRepository;
        this.tripDurationAndDistanceRepository = tripDurationAndDistanceRepository;
        this.tripAgreementRepository = tripAgreementRepository;
        this.amentitiesRepository = amentitiesRepository;
        this.travelPointsService = travelPointsService;
        this.userService = userService;
        this.jwtUserService = jwtUserService;
        this.autosService = autosService;
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

            Autos userAuto = autosService.getAutoByIdNotDTO(
                    Long.parseLong(tripData.get("carId").toString())
            );

            trip.setAutos(userAuto);
            trip.setStartTravelPoint(travelPointFrom);
            trip.setFinishTravelPoint(travelPointTo);
            trip.setPassengerCount((Integer) tripData.get("passengers"));
            trip.setPrice(convertToDouble(tripData.get("price")));
            trip.setAvailableSeats((Integer) tripData.get("passengers"));
            String selectedTime = (String) tripData.get("selectedTime");
            String selectedDate = (String) tripData.get("date");


            LocalTime time = LocalTime.parse(selectedTime, DateTimeFormatter.ofPattern("HH:mm"));
            LocalDate date = LocalDate.parse(selectedDate, DateTimeFormatter.ISO_LOCAL_DATE);

            trip.setDepartureTime(time);
            trip.setDepartureDate(date);

            trip.setUser(userService.getUserById(userId).get());

            Map<String, Object> optionsData = (Map<String, Object>) tripData.get("options");

            Options options = new Options();
            options.setMaxTwoPassengers((Boolean) optionsData.get("maxTwoPassengers"));
            options.setWomenOnly((Boolean) optionsData.get("womenOnly"));
            options.setTrip(trip);

            Map<String, Object> am = (Map<String, Object>) tripData.get("amenities");
            Amentities amentities = new Amentities();
            amentities.setSmoking((Boolean) am.get("smoking"));
            amentities.setWifi((Boolean) am.get("wifi"));
            amentities.setETickets((Boolean) am.get("eTickets"));
            amentities.setAirConditioning((Boolean) am.get("airConditioning"));
            amentities.setFoodProvided((Boolean) am.get("food"));
            amentities.setPetsAllowed((Boolean) am.get("petsAllowed"));

            Map<String, Object> selectedRoute = (Map<String, Object>) tripData.get("selectedRoute");
            TripDurationAndDistance  tripDurationAndDistance = new TripDurationAndDistance();

            tripDurationAndDistance.setDistance((String) selectedRoute.get("distance"));
            tripDurationAndDistance.setDuration((String) selectedRoute.get("duration"));

            TripAgreement tripAgreement = new TripAgreement();
            tripAgreement.setIsAgreed(tripData.get("selectBooking") != "each");

            Trips savedTrip = tripService.save(trip);
            tripDurationAndDistance.setTrip(savedTrip);
            TripDurationAndDistance t = tripDurationAndDistanceRepository.save(tripDurationAndDistance);
            tripAgreement.setTrip(savedTrip);
            TripAgreement tr = tripAgreementRepository.save(tripAgreement);
            amentities.setTrip(savedTrip);
            Amentities a = amentitiesRepository.save(amentities);
            options.setTrip(savedTrip);
            Options op = optionsRepository.save(options);
            return ResponseEntity.ok(savedTrip.getId());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/getTripById/{id}")
    public ResponseEntity<TripDto> getTripsById(@PathVariable("id") Long id)
    {
        try {
            TripDto trip = tripService.findByIdDTO(id);
            if (trip != null) {
                return ResponseEntity.ok(trip);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/getSearchTrip")
    public List<Trips> getSearchTrip(@RequestBody SearchTripRequest request) {

        System.out.println("Searching trips from: " + request.getFrom() + " to: " + request.getTo());
        System.out.println("Passengers: " + request.getPassengers().size());

        String startCity = request.getFrom().getCity();
        String startState = request.getFrom().getCountry();
        String finishCity = request.getTo().getCity();
        String finishState = request.getTo().getCountry();

        LocalDate departureDate = LocalDate.parse(request.getDate().toString(), DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println("Departure Date: " + departureDate);


        int passengerCount = request.getPassengers().size();
        List<Trips> l = tripService.searchTrips(departureDate, passengerCount, startCity, startState, finishCity, finishState);
        System.out.println(l);
       for(Trips t : l)
       {System.out.println(t.getId());
           System.out.println(t.getAvailableSeats());
           System.out.println(t.getFinishTravelPoint().getCity());
           System.out.println(t.getUser().getName());

       }
        return l;
    }


}
