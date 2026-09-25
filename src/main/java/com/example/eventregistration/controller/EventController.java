package com.example.eventregistration.controller;

import com.example.eventregistration.entity.Event;
import com.example.eventregistration.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    // Show all events
    @GetMapping("/events")
    public String eventsPage(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        return "events";
    }

    // Show event details
    @GetMapping("/events/{id}")
    public String eventDetails(@PathVariable Long id, Model model) {

        Event event = eventService.getEventById(id);

        if (event == null) {
            return "redirect:/events";
        }

        model.addAttribute("event", event);
        return "event-details";
    }
}