package org.booking.spring.repositories;

import org.booking.spring.models.user.PhoneVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneVerificationRepository extends JpaRepository<PhoneVerification, Long> {
    PhoneVerification findByUserId(Long userId);
}
