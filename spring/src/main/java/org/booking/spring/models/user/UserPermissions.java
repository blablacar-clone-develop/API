package org.booking.spring.models.user;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "user_permissions")
@Data
public class UserPermissions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "userPermissions")
    private Set<User> users;
}
