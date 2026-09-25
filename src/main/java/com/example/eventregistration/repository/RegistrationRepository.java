package com.example.eventregistration.repository;

import com.example.eventregistration.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    List<Registration> findByUserId(Long userId);

    List<Registration> findByEventId(Long eventId);

    boolean existsByUserIdAndEventId(Long userId, Long eventId);
}