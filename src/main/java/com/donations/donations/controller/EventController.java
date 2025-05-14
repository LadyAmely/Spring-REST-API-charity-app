package com.donations.donations.controller;

import com.donations.donations.logs.LogService;
import com.donations.donations.model.Event;
import com.donations.donations.service.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final LogService logService;
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService, LogService logService) {
        this.eventService = eventService;
        this.logService = logService;

    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }


    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event createdEvent = eventService.createEvent(event.getName(), event.getCurrency());
        logService.logInfo("Event created successfully: " + createdEvent.getName());
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

}
