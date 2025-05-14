package com.donations.donations.controller;

import com.donations.donations.dto.FinancialReportDTO;
import com.donations.donations.logs.LogService;
import com.donations.donations.service.report.FinancialReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/financial-report")
public class FinancialReportController {

    private final LogService logService;
    private final FinancialReportService financialReportService;

    @Autowired
    public FinancialReportController(LogService logService, FinancialReportService financialReportService){
        this.logService = logService;
        this.financialReportService = financialReportService;
    }

    @GetMapping
    public ResponseEntity<List<FinancialReportDTO>> getReport(){
        logService.logInfo("Fetching financial report.");
        List<FinancialReportDTO> report = financialReportService.getFinancialReport();
        return ResponseEntity.ok(report);
    }
}
