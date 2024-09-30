package org.booking.spring.repositories;

import org.booking.spring.models.user.UserVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserVerificationRepository extends JpaRepository<UserVerification, Long> {

    @Query("SELECT uv FROM UserVerification uv WHERE uv.userId = :userId")
    UserVerification findByUserId(@Param("userId") Long userId);

}
