package com.donations.donations.controller;

import com.donations.donations.logs.LogService;
import com.donations.donations.model.Event;
import com.donations.donations.service.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event createdEvent = eventService.createEvent(event.getName(), event.getCurrency());
        logService.logInfo("Event created successfully: " + createdEvent.getName());  // Zamiast logger.info()
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

}
