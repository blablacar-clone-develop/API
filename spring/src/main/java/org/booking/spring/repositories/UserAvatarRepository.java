package org.booking.spring.repositories;

import jakarta.transaction.Transactional;
import org.booking.spring.models.user.AccountInfo;
import org.booking.spring.models.user.Avatars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserAvatarRepository extends JpaRepository<Avatars, Long> {

    // Метод для видалення аватарки по user_id
    @Modifying
    @Transactional
    @Query("DELETE FROM Avatars a WHERE a.user.id = :userId")
    int deleteByUserId(@Param("userId") Long userId);

}
