package org.booking.spring.repositories;

import org.booking.spring.models.user.UsersContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersContactRepository extends JpaRepository<UsersContact, Long> {
}
