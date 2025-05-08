package com.donations.donations.service.event;

import com.donations.donations.model.Event;

public interface IEventService {

    public Event createEvent(String name, String currency);
}
