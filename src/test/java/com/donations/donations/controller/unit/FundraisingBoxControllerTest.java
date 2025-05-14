package com.donations.donations.controller.unit;

import com.donations.donations.controller.FundraisingBoxController;
import com.donations.donations.logs.LogService;
import com.donations.donations.model.FundraisingBox;
import com.donations.donations.service.fundraisingBox.FundraisingBoxService;
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

public class FundraisingBoxControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FundraisingBoxService boxService;

    @Mock
    private LogService logService;

    @InjectMocks
    private FundraisingBoxController boxController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(boxController).build();
    }

    @Test
    public void testGetAllFundraisingBoxes() throws Exception {
        FundraisingBox box1 = new FundraisingBox();
        box1.setId(1L);
        box1.setAssigned(false);
        box1.setEmpty(true);
        box1.setCurrency("USD");

        FundraisingBox box2 = new FundraisingBox();
        box2.setId(2L);
        box2.setAssigned(true);
        box2.setEmpty(false);
        box2.setCurrency("EUR");

        List<FundraisingBox> boxes = Arrays.asList(box1, box2);

        when(boxService.getAllFundraisingBoxes()).thenReturn(boxes);

        mockMvc.perform(get("/api/fundraising-boxes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(boxService, times(1)).getAllFundraisingBoxes();
    }

    @Test
    public void testCreateFundraisingBox() throws Exception {
        FundraisingBox newBox = new FundraisingBox();
        newBox.setCurrency("USD");

        FundraisingBox createdBox = new FundraisingBox();
        createdBox.setId(1L);
        createdBox.setCurrency("USD");

        when(boxService.createFundraisingBox("USD")).thenReturn(createdBox);

        mockMvc.perform(post("/api/fundraising-boxes")
                        .param("currency", "USD"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.currency").value("USD"));

        verify(boxService, times(1)).createFundraisingBox("USD");
        verify(logService, times(1)).logInfo("Starting creation of new fundraising field with currency: USD");
    }

    @Test
    public void testAssignFundraisingBoxToEvent() throws Exception {
        mockMvc.perform(post("/api/fundraising-boxes/1/assign/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("The gathering point has been assigned to the event."));
        verify(boxService, times(1)).assignFundraisingBoxToEvent(1L, 1L);
    }


    @Test
    public void testUnregisterFundraisingBox() throws Exception {

        mockMvc.perform(delete("/api/fundraising-boxes/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("The collection point has been deregistered."));

        verify(boxService, times(1)).unregisterFundraisingBox(1L);
    }
}

