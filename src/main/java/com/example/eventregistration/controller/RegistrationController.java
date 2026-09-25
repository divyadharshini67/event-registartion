package com.example.eventregistration.controller;

import com.example.eventregistration.entity.Registration;
import com.example.eventregistration.service.EventService;
import com.example.eventregistration.service.RegistrationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private EventService eventService;

    // Register for an event
    @PostMapping("/events/register")
    public String registerEvent(
            @RequestParam Long eventId,
            HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        // Check duplicate registration
        Registration registration =
                registrationService.registerUser(userId, eventId);

        if (registration == null) {
            return "redirect:/events/" + eventId + "?error=alreadyRegistered";
        }

        return "redirect:/my-registrations";
    }

    // View user's registrations
    @GetMapping("/my-registrations")
    public String myRegistrations(
            HttpSession session,
            Model model) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        List<Registration> registrations =
                registrationService.getUserRegistrations(userId);

        model.addAttribute("registrations", registrations);
        model.addAttribute("eventService", eventService);

        return "my-registrations";
    }

    // Cancel registration
    @GetMapping("/registration/cancel/{id}")
    public String cancelRegistration(
            @PathVariable Long id,
            HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        registrationService.cancelRegistration(id);

        return "redirect:/my-registrations";
    }
}