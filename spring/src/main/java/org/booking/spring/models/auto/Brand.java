package org.booking.spring.models.auto;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "carsBrands")
@Data
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

}
