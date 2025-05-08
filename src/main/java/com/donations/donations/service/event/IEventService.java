package com.donations.donations.service.event;

import com.donations.donations.model.Event;

import java.util.List;

public interface IEventService {

    public Event createEvent(String name, String currency);
    public List<Event> getAllEvents();
}
