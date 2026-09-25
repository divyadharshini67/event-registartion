package com.example.eventregistration.controller;

import com.example.eventregistration.entity.Admin;
import com.example.eventregistration.entity.Event;
import com.example.eventregistration.service.AdminService;
import com.example.eventregistration.service.EventService;
import com.example.eventregistration.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private EventService eventService;

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/admin/login")
    public String adminLoginPage() {
        return "admin-login";
    }

    @PostMapping("/admin/login")
    public String adminLogin(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {

        Admin admin = adminService.login(username, password);

        if (admin != null) {
            return "redirect:/admin/dashboard";
        }

        model.addAttribute("error", "Invalid username or password");
        return "admin-login";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        return "admin-dashboard";
    }

    // Add Event
    @GetMapping("/admin/events/add")
    public String addEventPage(Model model) {
        model.addAttribute("event", new Event());
        return "add-event";
    }

    @PostMapping("/admin/events/add")
    public String addEvent(@ModelAttribute Event event) {
        eventService.saveEvent(event);
        return "redirect:/admin/dashboard";
    }

    // Edit Event - Show Edit Page
    @GetMapping("/admin/events/edit/{id}")
    public String editEventPage(
            @PathVariable Long id,
            Model model) {

        Event event = eventService.getEventById(id);

        if (event == null) {
            return "redirect:/admin/dashboard";
        }

        model.addAttribute("event", event);

        return "edit-event";
    }

    // Edit Event - Update Event
    @PostMapping("/admin/events/edit")
    public String editEvent(@ModelAttribute Event event) {

        eventService.saveEvent(event);

        return "redirect:/admin/dashboard";
    }

    // Delete Event
    @GetMapping("/admin/events/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {

        eventService.deleteEvent(id);

        return "redirect:/admin/dashboard";
    }

    // View registrations for an event
    @GetMapping("/admin/events/{id}/registrations")
    public String viewRegistrations(
            @PathVariable Long id,
            Model model) {

        Event event = eventService.getEventById(id);

        if (event == null) {
            return "redirect:/admin/dashboard";
        }

        model.addAttribute(
                "registrations",
                registrationService.getEventRegistrations(id)
        );

        model.addAttribute("event", event);

        // Send RegistrationService to HTML
        model.addAttribute("registrationService", registrationService);

        return "event-registrations";
    }
}