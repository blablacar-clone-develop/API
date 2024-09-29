package org.booking.spring.requests.auth;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class SearchTripRequest {
    private Location from;
    private Location to;
    private LocalDate date;
    private List<Passenger> passengers;

    @Getter
    @Setter
    public static class Location {
        private String city;
        private String country;
        private String address;
    }

    @Getter
    @Setter
    public static class Passenger {
        private String name;
        private boolean isChecked;
    }
}
