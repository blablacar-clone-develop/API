package org.booking.spring.responses.DTO;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.booking.spring.models.trips.TripDurationAndDistance;

@Getter
@Setter
public class TripDurationAndDistanceDTO {
    private String duration;
    private String distance;

    public TripDurationAndDistanceDTO(TripDurationAndDistance tripDurationAndDistance) {
        this.duration = tripDurationAndDistance.getDuration();
        this.distance = tripDurationAndDistance.getDistance();
    }
}
