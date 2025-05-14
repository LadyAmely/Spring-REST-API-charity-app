package com.donations.donations.model.unit;

import com.donations.donations.model.Event;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

public class EventTest {

    @Test
    public void testEventCreation() {
        Event event = new Event();

        event.setName("Charity Event");
        event.setCurrency("USD");
        event.setBalance(new BigDecimal("100.00"));

        assertThat(event.getName()).isEqualTo("Charity Event");
        assertThat(event.getCurrency()).isEqualTo("USD");
        assertThat(event.getBalance()).isEqualByComparingTo("100.00");
    }


    @Test
    public void testSettersAndGetters() {
        Event event = new Event();

        event.setName("Fundraiser");
        event.setCurrency("EUR");
        event.setBalance(new BigDecimal("50.00"));

        assertThat(event.getName()).isEqualTo("Fundraiser");
        assertThat(event.getCurrency()).isEqualTo("EUR");
        assertThat(event.getBalance()).isEqualByComparingTo("50.00");
    }
}
