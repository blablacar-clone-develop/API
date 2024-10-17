package org.booking.spring.responses.DTO;

import lombok.Getter;
import lombok.Setter;
import org.booking.spring.models.trips.TravelPoints;

@Getter
@Setter
public class TravelPointDto {
    private Double latitude;
    private Double longitude;
    private String city;
    private String state;


    public TravelPointDto(TravelPoints travelPoints) {
        this.latitude = travelPoints.getLatitude();
        this.longitude = travelPoints.getLongitude();
        this.city = travelPoints.getCity();
        this.state = travelPoints.getState();
    }
}
