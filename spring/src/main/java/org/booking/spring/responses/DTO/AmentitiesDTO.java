package org.booking.spring.responses.DTO;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.booking.spring.models.trips.Amentities;

@Getter
@Setter
public class AmentitiesDTO {
    private boolean wifi;
    private boolean eTickets;
    private boolean airConditioning;
    private boolean smoking;
    private boolean petsAllowed;
    private boolean foodProvided;

    public AmentitiesDTO(Amentities amentities) {
        this.wifi = amentities.isWifi();
        this.eTickets = amentities.isETickets();
        this.airConditioning = amentities.isAirConditioning();
        this.smoking = amentities.isSmoking();
        this.petsAllowed = amentities.isPetsAllowed();
        this.foodProvided = amentities.isFoodProvided();
    }
}
