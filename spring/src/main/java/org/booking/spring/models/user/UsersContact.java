package org.booking.spring.models.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Entity
@Table(name = "users_contact")
@Getter
@Setter
public class UsersContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @Column(name = "country_code", nullable = false)
    private String countryCode;
}
