package org.booking.spring.models.user;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import org.booking.spring.models.baseEntity.BaseEntity;

@Entity
@Table(name = "users_avatars")
@Data
public class Avatars extends BaseEntity {

    // Один користувач має одну аватарку
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String url;  // URL аватарки

}
