package com.donations.donations.controller.unit;


import com.donations.donations.controller.FinancialReportController;
import com.donations.donations.dto.FinancialReportDTO;
import com.donations.donations.logs.LogService;
import com.donations.donations.service.report.FinancialReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FinancialReportControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FinancialReportService financialReportService;

    @Mock
    private LogService logService;

    @InjectMocks
    private FinancialReportController financialReportController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(financialReportController).build();
    }


    @Test
    public void testGetReport_NoData() throws Exception {

        List<FinancialReportDTO> reports = Arrays.asList();
        when(financialReportService.getFinancialReport()).thenReturn(reports);

        mockMvc.perform(get("/api/financial-report"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        verify(financialReportService, times(1)).getFinancialReport();
        verify(logService, times(1)).logInfo("Fetching financial report.");
    }

}

