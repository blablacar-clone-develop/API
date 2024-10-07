package org.booking.spring.models.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.booking.spring.models.baseEntity.BaseEntity;


@Entity
@Table(name = "users_avatars")
@Data
@Setter
@Getter
public class Avatars extends BaseEntity {

    // Один користувач має одну аватарку
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference // Додаємо @JsonBackReference для розриву циклу серіалізації
    private User user;


    private String url;  // URL аватарки

    @Column(name = "file_name")
    private String filename; //
}
