package com.donations.donations.controller.unit;

import com.donations.donations.controller.EventController;
import com.donations.donations.logs.LogService;
import com.donations.donations.model.Event;
import com.donations.donations.service.event.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EventControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EventService eventService;

    @Mock
    private LogService logService;

    @InjectMocks
    private EventController eventController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
    }

    @Test
    public void testGetAllEvents() throws Exception {

        Event event1 = new Event();
        event1.setEvent_id(1L);
        event1.setName("Event 1");
        event1.setCurrency("USD");

        Event event2 = new Event();
        event2.setEvent_id(2L);
        event2.setName("Event 2");
        event2.setCurrency("EUR");

        List<Event> events = Arrays.asList(event1, event2);
        when(eventService.getAllEvents()).thenReturn(events);

        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Event 1"))
                .andExpect(jsonPath("$[1].name").value("Event 2"));

        verify(eventService, times(1)).getAllEvents();
    }

    @Test
    public void testCreateEvent() throws Exception {

        Event event = new Event();
        event.setName("New Event");
        event.setCurrency("USD");

        Event createdEvent = new Event();
        createdEvent.setEvent_id(1L);
        createdEvent.setName("New Event");
        createdEvent.setCurrency("USD");

        when(eventService.createEvent(event.getName(), event.getCurrency())).thenReturn(createdEvent);

        mockMvc.perform(post("/api/events")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(event)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Event"))
                .andExpect(jsonPath("$.currency").value("USD"));

        verify(eventService, times(1)).createEvent(event.getName(), event.getCurrency());
        verify(logService, times(1)).logInfo("Event created successfully: New Event");
    }
}
