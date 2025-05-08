package com.donations.donations.service.event;

import com.donations.donations.model.Event;
import com.donations.donations.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService implements IEventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event createEvent(String name, String currency) {
        Event event = new Event();
        event.setName(name);
        event.setCurrency(currency);
        return eventRepository.save(event);
    }

}
