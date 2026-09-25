package com.example.eventregistration.service;

import com.example.eventregistration.entity.Registration;
import com.example.eventregistration.entity.User;
import com.example.eventregistration.repository.RegistrationRepository;
import com.example.eventregistration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private UserRepository userRepository;

    public Registration registerUser(Long userId, Long eventId) {

        // Prevent duplicate registration
        if (registrationRepository.existsByUserIdAndEventId(userId, eventId)) {
            return null;
        }

        Registration registration = new Registration();

        registration.setUserId(userId);
        registration.setEventId(eventId);

        return registrationRepository.save(registration);
    }

    public List<Registration> getUserRegistrations(Long userId) {
        return registrationRepository.findByUserId(userId);
    }

    public List<Registration> getEventRegistrations(Long eventId) {
        return registrationRepository.findByEventId(eventId);
    }

    public void cancelRegistration(Long id) {
        registrationRepository.deleteById(id);
    }

    // Get user details
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
}