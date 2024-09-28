package org.booking.spring.responses.DTO;

import lombok.Getter;
import lombok.Setter;
import org.booking.spring.models.trips.TravelPoints;

@Setter
@Getter
public class TripDto {
    private Long id;
    private double price;
    private TravelPoints  start_travel_point_id;
    private TravelPoints  finish_travel_point_id;
    private int passenger_count;
    private int available_seats;
}
