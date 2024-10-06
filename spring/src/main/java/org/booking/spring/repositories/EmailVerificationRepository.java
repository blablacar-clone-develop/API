package org.booking.spring.repositories;

import org.booking.spring.models.user.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {

    @Query("SELECT ev FROM EmailVerification ev WHERE ev.userId = :userId")
    EmailVerification findByUserId(Long userId);
}
