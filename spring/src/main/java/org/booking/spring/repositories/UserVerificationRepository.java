package org.booking.spring.repositories;

import org.booking.spring.models.user.UserVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVerificationRepository extends JpaRepository<UserVerification, Long> {

    UserVerification findByUserId(Long userId);

}