package org.booking.spring.models.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.booking.spring.models.baseEntity.BaseEntity;

@Entity
@Table(name = "users_avatars")
@Data
public class Avatars extends BaseEntity {



}
