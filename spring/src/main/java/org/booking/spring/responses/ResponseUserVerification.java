package org.booking.spring.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseUserVerification {
    private Boolean emailVerified;
    private Boolean phoneVerified;
    private Boolean documentVerified;
}
