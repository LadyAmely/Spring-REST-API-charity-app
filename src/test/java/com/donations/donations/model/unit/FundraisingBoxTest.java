package com.donations.donations.model.unit;

import com.donations.donations.model.Event;
import com.donations.donations.model.FundraisingBox;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class FundraisingBoxTest {

    @Test
    public void testFundraisingBoxCreation() {

        Event event = new Event();
        event.setEvent_id(1L);

        FundraisingBox fundraisingBox = new FundraisingBox();
        fundraisingBox.setId(1L);
        fundraisingBox.setEvent(event);
        fundraisingBox.setAssigned(true);
        fundraisingBox.setEmpty(false);
        fundraisingBox.setAmount(new BigDecimal("100.00"));
        fundraisingBox.setCurrency("USD");
        fundraisingBox.setDeleted(false);

        assertNotNull(fundraisingBox);
        assertEquals(1L, fundraisingBox.getId());
        assertEquals(event, fundraisingBox.getEvent());
        assertTrue(fundraisingBox.isAssigned());
        assertFalse(fundraisingBox.isEmpty());
        assertEquals(new BigDecimal("100.00"), fundraisingBox.getAmount());
        assertEquals("USD", fundraisingBox.getCurrency());
        assertFalse(fundraisingBox.isDeleted());
    }

}
