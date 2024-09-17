package org.booking.spring.models.auto;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "carsModels")
@Data
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;


}

