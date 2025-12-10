package com.springsecurity.Repository;

import com.springsecurity.entities.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipientRepository extends JpaRepository<Recipient,Long> {
}
