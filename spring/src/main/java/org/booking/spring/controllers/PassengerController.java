package org.booking.spring.controllers;

import org.booking.spring.models.trips.Passenger;
import org.booking.spring.services.PassengerService;
import org.booking.spring.services.TripService;
import org.booking.spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;
    @Autowired
    private TripService tripService;
    @Autowired
    private UserService userService;
    @GetMapping("/{tripId}")
    public ResponseEntity<List<Long>> getPassengersByTripId(@PathVariable Long tripId) {
        List<Long> passengerIds = passengerService.getPassengerIdsByTripId(tripId);
        return ResponseEntity.ok(passengerIds);
    }

    @PostMapping("/save")
    public ResponseEntity<String> bookTrip(@RequestBody Map<String, Object> bookingRequest) {
        Long userId = Long.valueOf(bookingRequest.get("userId").toString());
        Long tripId = Long.valueOf(bookingRequest.get("tripId").toString());
        List<Map<String, Object>> passengers = (List<Map<String, Object>>) bookingRequest.get("passengers");

        for (Map<String, Object> passengerData : passengers) {
            String passengerType = (String) passengerData.get("type");
            if (passengerType != null) {
                Passenger additionalPassenger = new Passenger();
                additionalPassenger.setTrip(tripService.findById(tripId).orElseThrow(() -> new RuntimeException("Trip not found")));
                additionalPassenger.setPassengerType(passengerType);
                additionalPassenger.setUser(userService.findUserById(userId));
                passengerService.save(additionalPassenger);
                tripService.changeCountPassenger(-1, tripId);
            }
        }

        return ResponseEntity.ok("Booking confirmed for trip ID: " + tripId + " with passengers.");
    }
}
